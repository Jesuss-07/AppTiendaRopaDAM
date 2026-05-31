import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarritoService } from '../../services/carrito.service';
import { DetallePedidoDTO } from '../../model/detalle-pedido.dto';
import { PedidoDTO } from '../../model/pedido.dto';
import { ProductoCarritoDTO } from '../../model/producto-carrito.dto';
import { Router } from '@angular/router';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({ 
    selector: 'app-carrito',
    templateUrl: './carrito.component.html',
    styleUrl: './carrito.component.css',
    standalone: true,
    imports: [
        CommonModule,
        RouterLink
    ]
})
export class CarritoComponent {

    rol: string | null = null;
    pedido: PedidoDTO | null = null;

    constructor(private carritoService: CarritoService, private router: Router, private authService: AuthService) {}

    ngOnInit() {
        this.rol = this.authService.getRol();
        console.log('Rol del usuario:', this.rol);
        this.carritoService.listarCarrito().subscribe({
            next: (data) => {
                this.pedido = data;
                console.log('Pedido cargado:', this.pedido);
            },
            error: (err) => {
                console.error('Error al cargar el carrito:', err);
            }
        });
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
                this.ngOnInit(); // Recargar el carrito
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