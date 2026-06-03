import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { switchMap, tap } from 'rxjs/operators';

import { UsuarioService } from '../../../services/usuario.service';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-listar-usuario',
  templateUrl: './listar.component.html',
  styleUrls: ['./listar.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    RouterLink
  ]
})

export class ListarUsuarioComponent {

  rol: string | null = null;
  cargando = true;

  private refresh$ = new BehaviorSubject<void>(undefined);

  usuarios$ = this.refresh$.pipe(
    tap(() => this.cargando = true),
    switchMap(() => this.usuarioService.listarUsuarios()),
    tap(() => this.cargando = false)
  );

  constructor(private router: Router, private authService: AuthService, private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    this.rol = this.authService.getRol();
    console.log('Rol del usuario:', this.rol);
  }

  eliminarUser(email: string): void {

    if (!confirm(`¿Bloquear al usuario ${email}?`)) {
      return;
    }

    this.usuarioService.bloquearUsuario(email).subscribe({
      next: () => {
        alert(`Se bloqueó el usuario ${email}`);
        this.refresh$.next();
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}