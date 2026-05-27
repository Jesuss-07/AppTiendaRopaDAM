import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

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

  constructor(private authService: AuthService, private router: Router) {}  

  ngOnInit(): void {
    this.rol = this.authService.getRol();
    console.log('Rol del usuario:', this.rol);
  }

  editarUser(): void {
    if (this.rol === 'ADMINISTRADOR') {}
    else if (this.rol === 'VENDEDOR') {
      this.router.navigate(['/editor/vendedor']);
    }
    else if (this.rol === 'CLIENTE') {
      this.router.navigate(['/editor/cliente']);
    }
  }

  logout(){
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}