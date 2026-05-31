import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ProductoService } from '../../../services/producto.service';
import { AuthService } from '../../../services/auth.service';
import { EditarProductoDTO } from '../../../model/editar-producto.dto';
import { FormsModule } from '@angular/forms';
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
export class DetalleComponent implements OnInit {

  rol: string | null = null;
  producto: EditarProductoDTO | null = null;
  cantidad: number = 1;
  loading = true;

  constructor(private route: ActivatedRoute, private productoService: ProductoService, private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.rol = this.authService.getRol();

    this.route.paramMap.subscribe(params => {
      const id = Number(params.get('id'));

      if (!id) {
        console.error('ID de producto inválido');
        this.loading = false;
        return;
      }

      this.cargarProducto(id);
    });
  }

  cargarProducto(id: number): void {
    console.log('Cargando producto con ID:', id);
    this.loading = true;
    this.producto = null;

    this.productoService.obtenerProducto(id).subscribe({
      next: (data) => {
        console.log('Producto cargado:', data);
        this.producto = data;
        this.loading = false;
      },
      error: (error) => {
        console.error(error);
        this.loading = false;
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

  anadirAlCarrito(id: number, cantidad: number): void {
    console.log('Añadiendo producto al carrito, ID:', id, 'Cantidad:', cantidad);
    console.log(this.producto);
    this.router.navigate(['/inicio']);
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}