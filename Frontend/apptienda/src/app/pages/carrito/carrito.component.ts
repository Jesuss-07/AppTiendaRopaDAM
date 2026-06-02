import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { BehaviorSubject, switchMap, tap } from 'rxjs';

import { CarritoService } from '../../services/carrito.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-carrito',
  templateUrl: './carrito.component.html',
  styleUrls: ['./carrito.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    RouterLink
  ]
})
export class CarritoComponent {

  rol: string | null = null;

  private refresh$ = new BehaviorSubject<void>(undefined);

  pedido$ = this.refresh$.pipe(
    switchMap(() => this.carritoService.listarCarrito())
  );

  constructor(
    private carritoService: CarritoService,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.rol = this.authService.getRol();
    console.log('Rol del usuario:', this.rol);
  }

  pagarPedido() {
    this.carritoService.pagarPedido().subscribe({
      next: () => {
        alert('Pedido pagado con éxito');
        this.router.navigate(['/inicio']);
      },
      error: (err) => {
        console.error('Error al pagar el pedido:', err);
        alert('Error al pagar el pedido');
      }
    });
  }

  eliminar(idDetallePedido: number) {
    this.carritoService.eliminarProducto(idDetallePedido).subscribe({
      next: () => {
        alert('Producto eliminado del carrito');
        this.refresh$.next(); // 🔥 recarga reactiva
      },
      error: (err) => {
        console.error('Error al eliminar el producto:', err);
        alert('Error al eliminar el producto');
      }
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}