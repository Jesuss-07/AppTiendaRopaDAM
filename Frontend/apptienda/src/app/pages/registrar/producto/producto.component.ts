import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';
import { ProductoService } from '../../../services/producto.service';
import { CrearProductoDTO } from '../../../model/crear-producto.dto';
import { Talla } from '../../../model/talla.enum';
import { CategoriaRopa } from '../../../model/categoria.enum';

@Component({
  selector: 'app-producto',
  templateUrl: './producto.component.html',
  styleUrls: ['./producto.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterModule
  ]
})
export class ProductoComponent {

    rol: string | null = null;
    protected producto: CrearProductoDTO = {
        nombreProducto: '',
        descripcion: '',
        precio: 0,
        stock: 0,
        talla: Talla.S,
        color: '',
        categoria: CategoriaRopa.HOMBRE,
        imagenProducto: ''
    };

    Talla = Talla;
    CategoriaRopa = CategoriaRopa;

    constructor(private authService: AuthService, private router: Router, private productoService: ProductoService) {}

    ngOnInit() {
        this.rol = this.authService.getRol();
    }

    editarUser() {
        this.router.navigate(['/editor/vendedor']);
    }

    crearProducto() {
        if (!this.producto.nombreProducto || !this.producto.color || this.producto.precio <= 0 || this.producto.stock <= 0) {
            alert('Rellena todos los campos obligatorios correctamente');
            return;
        }

        this.productoService.anadirProducto(this.producto).subscribe({
            next: () => {
            alert('Producto creado con éxito');
            this.router.navigate(['/inicio']);
            },
            error: (err) => {
            console.error('Error al crear producto', err);
            alert('Error al crear producto');
            }
        });

    }

    paginaEmpresa() {}

    logout() {
        this.authService.logout();
        this.router.navigate(['/login']);
    }

}