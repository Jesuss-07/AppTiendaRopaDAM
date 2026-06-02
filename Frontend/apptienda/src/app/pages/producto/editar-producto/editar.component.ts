import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { EmpresaService } from '../../../services/empresa.service';
import { ProductoService } from '../../../services/producto.service';
import { EditarProductoDTO } from '../../../model/editar-producto.dto';
import { Talla } from '../../../model/talla.enum';
import { CategoriaRopa } from '../../../model/categoria.enum';

@Component({
    selector: 'app-editar-producto',
    templateUrl: './editar.component.html',
    styleUrls: ['./editar.component.css'],
    standalone: true,
    imports: [
        CommonModule,
        FormsModule,
        RouterModule
    ]
})

export class EditarProductoComponent {

    productoId: string | null = null;
    rol: string | null = null;
    producto: EditarProductoDTO = {
    nombreProducto: '',
    descripcion: '',
    precio: 0,
    stock: 0,
    talla: '',
    color: '',
    categoria: '',
    imagenProducto: ''
    };
    
    Talla = Talla;
    CategoriaRopa = CategoriaRopa;

    constructor(private route: ActivatedRoute, private authService: AuthService, private router: Router, private productoService: ProductoService, private empresaService: EmpresaService) {}

    ngOnInit() {
        this.rol = this.authService.getRol();
        console.log('Rol del usuario:', this.rol);
        const id = this.route.snapshot.paramMap.get('id');

        if (id) {
            this.productoId = id;

            this.productoService.obtenerProducto(+id).subscribe({
            next: (data) => {
                console.log('Producto cargado:', data);
                this.producto = data;
            },
            error: (err) => {
                console.error('Error cargando producto', err);
            }
            });
        }
    }

    editarProducto() {
        if (!this.productoId) return;

        this.productoService.editarProducto(+this.productoId, this.producto).subscribe({
            next: () => {
            alert('Producto actualizado');
            this.router.navigate(['/productos']);
            },
            error: (err) => {
            console.error(err);
            alert('Error al actualizar');
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

    editarUser() {
        if(this.rol === 'ADMINISTRADOR') {
            this.router.navigate(['/editor/administrador']);
        } else if(this.rol === 'VENDEDOR') {
            this.router.navigate(['/editor/vendedor']);
        }
    }

    logout() {
        this.authService.logout();
        this.router.navigate(['/login']);
    }

}