import { Component } from '@angular/core';
import { Router, RouterLink, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { BehaviorSubject, Observable, switchMap, tap, map, startWith } from 'rxjs';

import { AuthService } from '../../services/auth.service';
import { EmpresaService } from '../../services/empresa.service';
import { ProductoService } from '../../services/producto.service';

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

  private refresh$ = new BehaviorSubject<void>(undefined);
  private categoria$ = new BehaviorSubject<string>('');

  cargando = true;

  productos$ = this.refresh$.pipe(
    tap(() => this.cargando = true),
    switchMap(() => {

      const rol = this.authService.getRol();

      if (rol === 'VENDEDOR') {
        return this.productoService.listarProductos();
      }

      return this.productoService.listarTodosLosProductos();
    }),
    tap(() => this.cargando = false)
  );

  productosFiltrados$ = this.productos$.pipe(
    switchMap(productos =>
      this.categoria$.pipe(
        startWith(''),
        map(categoria => {

          if (!categoria) return productos;

          return productos.filter(p =>
            (p.categoria ?? '').toUpperCase() === categoria.toUpperCase()
          );
        })
      )
    )
  );

  constructor(
    private authService: AuthService,
    private router: Router,
    private empresaService: EmpresaService,
    private productoService: ProductoService
  ) {}

  ngOnInit(): void {
    this.rol = this.authService.getRol();
    console.log(this.rol);
  }

  filtrarCategoria(categoria: string): void {
    this.categoria$.next(categoria);
  }

  recargar(): void {
    this.refresh$.next();
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

  editarUser(): void {
    if (this.rol === 'ADMINISTRADOR') {
      this.router.navigate(['/editor/administrador']);
    } else if (this.rol === 'VENDEDOR') {
      this.router.navigate(['/editor/vendedor']);
    } else if (this.rol === 'CLIENTE') {
      this.router.navigate(['/editor/cliente']);
    }
  }

  detalleProducto(id: number): void {
    this.router.navigate(['/producto/detalle', id]);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}