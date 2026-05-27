import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../services/auth.service';
import { EmpresaService } from '../../../services/empresa.service';
import { EmpresaDTO } from '../../../model/empresa.dto';
import { Observable, BehaviorSubject, switchMap, tap } from 'rxjs';

@Component({
  selector: 'app-empresa',
  templateUrl: './empresa.component.html',
  styleUrls: ['./empresa.component.css'],
  standalone: true,
  imports: [
    CommonModule
  ]
})
export class EmpresaComponent {

  private refresh$ = new BehaviorSubject<void>(undefined);

  empresas$: Observable<EmpresaDTO[]> = this.refresh$.pipe(
    switchMap(() => this.empresaService.obtenerEmpresas()),
    tap(() => this.cargando = false)
  );
  rol: string | null = null;

  cargando = true;

  constructor(private router: Router, private authService: AuthService, private empresaService: EmpresaService) {}

  ngOnInit() {
    this.rol = this.authService.getRol();

  }

  editarEmpresa(id: number) {
    this.router.navigate(['/empresa/editar', id]);
  }

  borrarEmpresa(id: number) {
    if (!confirm('¿Estás seguro de que deseas eliminar esta empresa?')) return;

    this.cargando = true;

    this.empresaService.borrarEmpresa(id).subscribe({
      next: () => this.refresh$.next(),
      error: err => {
        console.error(err);
        this.cargando = false;
      }
    });
  }

  nuevaEmpresa() {
    this.router.navigate(['/registro/empresa']);
  }

  verEmpresa(id: number) {
    this.router.navigate(['/empresa/vista', id]);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}