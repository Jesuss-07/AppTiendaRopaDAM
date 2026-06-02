import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { EmpresaService } from '../../../services/empresa.service';
import { Router } from '@angular/router';
import { ProductoService } from '../../../services/producto.service';
import { CrearProductoDTO } from '../../../model/crear-producto.dto';
import { CrearProductoAdminDTO } from '../../../model/crear-producto-admin.dto';
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
        imagenProducto: '',
        cifEmpresa: ''
    };

    Talla = Talla;
    CategoriaRopa = CategoriaRopa;

    constructor(private authService: AuthService, private router: Router, private productoService: ProductoService, private empresaService: EmpresaService) {}

    ngOnInit() {
        this.rol = this.authService.getRol();
    }

    editarUser() {
        this.router.navigate(['/editor/vendedor']);
    }

    crearProducto() {

        const esAdmin = this.rol === 'ADMINISTRADOR';

        if (
            !this.producto.nombreProducto ||
            !this.producto.color ||
            this.producto.precio <= 0 ||
            this.producto.stock <= 0 ||
            (esAdmin && !this.producto.cifEmpresa)
        ) {
            alert('Rellena todos los campos obligatorios correctamente');
            return;
        }

        const request = esAdmin
            ? this.productoService.anadirProductoAdmin(this.producto as CrearProductoAdminDTO)
            : this.productoService.anadirProductoVendedor(this.producto as CrearProductoDTO);

        request.subscribe({
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

    paginaEmpresa(): void {
        if (this.rol === 'ADMINISTRADOR') {
        this.router.navigate(['/empresa']);
        } else if (this.rol === 'VENDEDOR') {
        this.irEmpresa();
        }
    }

    irEmpresa(): void {
        this.empresaService.obtenerEmpresaPorIdVendedor().subscribe({
        next: (idEmpresa) => {
            this.router.navigate(['/empresa/vista', idEmpresa]);
        },
        error: (err) => {
            console.error('Error al obtener empresa:', err);
        }
        });
    }
    logout() {
        this.authService.logout();
        this.router.navigate(['/login']);
    }

}