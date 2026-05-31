import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { UsuarioService } from '../../../services/usuario.service';
import { AuthService } from '../../../services/auth.service';
import { UsuarioDTO } from '../../../model/Usuario.dto';

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
    usuarios: UsuarioDTO[] = [];

    constructor(private router: Router, private authService: AuthService, private usuarioService: UsuarioService) { }

    ngOnInit(): void {
        this.rol = this.authService.getRol();
        console.log('Rol del usuario:', this.rol);

        this.usuarioService.listarUsuarios().subscribe({
            next: (usuarios) => {
                this.usuarios = usuarios;
                console.log('Usuarios:', usuarios);
            },
            error: (error) => {
                console.error('Error al cargar usuarios:', error);
            }
        });
    }

    editarUser(id: number){}

    eliminarUser(email: string){
        this.usuarioService.bloquearUsuario(email);
        alert("Se bloqueo el usuario " + email);
        this.ngOnInit;
    }

    logout(): void {
        this.authService.logout();
        this.router.navigate(['/login']);
    }

}
