import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { EmpresaService } from '../../services/empresa.service';
import { EmpresaDTO } from '../../model/empresa.dto';

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

  empresas: EmpresaDTO[] = [];
  rol: string | null = null;
  cargando = true;

  constructor(private router: Router, private authService: AuthService, private empresaService: EmpresaService) {}

  ngOnInit() {
    this.rol = this.authService.getRol();
    this.cargarEmpresas();
  }

  cargarEmpresas() {
    this.empresaService.obtenerEmpresas().subscribe({
      next: (data) => {
        this.empresas = data;
        this.cargando = false;
        console.log("✅ RESPONSE OBJETO:", this.empresas);
    },
      error: (err) => {
        console.error('Error al cargar las empresas:', err);
        this.cargando = false;
      }
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}