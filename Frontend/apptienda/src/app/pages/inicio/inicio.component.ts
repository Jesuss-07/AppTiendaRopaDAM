import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { EmpresaService } from '../../services/empresa.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Observable } from 'rxjs';
import { ProductoService } from '../../services/producto.service';
import { ListarProductosVendedorDTO } from '../../model/Listar-productos-vendedor.dto';
import { ChangeDetectorRef } from '@angular/core';
@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
    styleUrls: ['./inicio.component.css'],
    standalone: true,
    imports: [
        RouterLink,
        CommonModule,
        RouterModule
    ]
})

  export class InicioComponent {

    rol: string | null = null;
    productos: ListarProductosVendedorDTO[] = [];

    constructor(private authService: AuthService, private router: Router, private productoService: ProductoService, private cdr: ChangeDetectorRef, private empresaService: EmpresaService) {} 
    
    ngOnInit(): void {
      this.rol = this.authService.getRol();
      console.log(this.rol)
      this.cargarProductos();
      this.cdr.detectChanges();
    }

    cargarProductos(): void {
      let request: Observable<ListarProductosVendedorDTO[]>;

      if (this.rol === 'ADMINISTRADOR') {
        request = this.productoService.listarTodosLosProductos();
      } else if (this.rol === 'VENDEDOR') {
        request = this.productoService.listarProductos();
      } else {
        request = this.productoService.listarTodosLosProductos();
      }

      request.subscribe({
        next: (data) => {
          this.productos = data;
          console.log('Productos obtenidos:', this.productos);
        },
        error: (error) => {
          console.error('Error al obtener los productos:', error);
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
          console.log(idEmpresa);
          this.router.navigate(['/empresa/vista', idEmpresa]);
        },
        error: (err) => {
          console.error('Error al obtener empresa:', err);
        }
      });
    }

    editarUser(): void {
      if (this.rol === 'ADMINISTRADOR') {
        this.router.navigate(['/editor/administrador']);
      }
      else if (this.rol === 'VENDEDOR') {
        this.router.navigate(['/editor/vendedor']);
      }
      else if (this.rol === 'CLIENTE') {
        console.log('Redirigiendo a la página de edición de usuario para cliente');
        this.router.navigate(['/editor/cliente']);
      }
    }

    detalleProducto(id: number): void {
      this.router.navigate(['/producto/detalle', id]);
    }

    logout(){
      this.authService.logout();
      this.router.navigate(['/login']);
    }
}