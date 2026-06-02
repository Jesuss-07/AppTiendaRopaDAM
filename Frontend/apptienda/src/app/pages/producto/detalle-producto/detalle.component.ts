import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { switchMap, map, tap } from 'rxjs/operators';
import { of, Observable } from 'rxjs';

import { ProductoService } from '../../../services/producto.service';
import { AuthService } from '../../../services/auth.service';
import { CarritoService } from '../../../services/carrito.service';
import { EditarProductoDTO } from '../../../model/editar-producto.dto';

@Component({
  selector: 'app-detalle',
  templateUrl: './detalle.component.html',
  styleUrls: ['./detalle.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    FormsModule
  ]
})
export class DetalleComponent {

  rol: string | null = null;
  cantidad: number = 1;
  loading = true;

  producto$!: Observable<EditarProductoDTO>;

  constructor(
    private route: ActivatedRoute,
    private productoService: ProductoService,
    private authService: AuthService,
    private router: Router,
    private carritoService: CarritoService
  ) {}

  ngOnInit(): void {

    this.rol = this.authService.getRol();

    this.producto$ = this.route.paramMap.pipe(
      map(params => Number(params.get('id'))),
      switchMap(id => {

        if (!id) {
          return of(null as any);
        }

        this.loading = true;

        return this.productoService.obtenerProducto(id).pipe(
          tap(() => this.loading = false)
        );
      })
    );
  }

  anadirAlCarrito(id: number, cantidad: number): void {

    this.carritoService.anadirProducto(id, cantidad).subscribe({
      next: () => {
        this.router.navigate(['/inicio']);
      },
      error: (error) => {
        console.error('Error al añadir al carrito:', error);
      }
    });
  }

  paginaEmpresa(): void {
    if (this.rol === 'ADMINISTRADOR') {
      this.router.navigate(['/empresa']);
    } else if (this.rol === 'VENDEDOR') {
      this.irEmpresa();
    }
  }

  irEmpresa(): void {}

  editarUser(): void {
    if (this.rol === 'ADMINISTRADOR') {
      this.router.navigate(['/editor/administrador']);
    } else if (this.rol === 'VENDEDOR') {
      this.router.navigate(['/editor/vendedor']);
    } else {
      this.router.navigate(['/editor/cliente']);
    }
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}