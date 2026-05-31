import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { ProductoService } from '../../../services/producto.service';
import { EmpresaService } from '../../../services/empresa.service';

import { Observable, BehaviorSubject, switchMap, tap } from 'rxjs';

@Component({
  selector: 'app-listar-producto',
  templateUrl: './producto.component.html',
  styleUrls: ['./producto.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    RouterModule
  ]
})
export class ListarProductoComponent implements OnInit {

  private refresh$ = new BehaviorSubject<void>(undefined);
  rol: string | null = null;
  cargando = true;

  productos$ = this.refresh$.pipe(
    switchMap(() => {
      if (this.rol === 'ADMINISTRADOR') {
        return this.productoService.listarTodosLosProductos();
      } else {
        return this.productoService.listarProductos();
      }
    }),
    tap(() => this.cargando = false)
  );

  constructor(
    private authService: AuthService,
    private router: Router,
    private productoService: ProductoService,
    private empresaService: EmpresaService
  ) {}

  ngOnInit() {
    this.rol = this.authService.getRol();
  }

  editarProducto(id: number) {
    this.router.navigate(['/producto/editar', id]);
    this.refresh$.next();
  }

  borrarProducto(id: number) {
    if (!confirm('¿Seguro que quieres eliminar este producto?')) return;

    this.cargando = true;

    this.productoService.eliminarProducto(id).subscribe({
      next: () => this.refresh$.next(),
      error: err => {
        console.error(err);
        this.cargando = false;
      }
    });
  }

  recargar() {
    this.cargando = true;
    this.refresh$.next();
  }

  editarUser() {
    this.router.navigate(['/editor/vendedor']);
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

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}