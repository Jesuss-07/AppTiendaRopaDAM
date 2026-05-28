import { Component } from "@angular/core";
import { CommonModule } from "@angular/common";
import { UsuarioService } from "../../../services/usuario.service";
import { AuthService } from "../../../services/auth.service";
import { Router, RouterModule } from "@angular/router";
import { FormsModule } from "@angular/forms";
import { EditarAdministradorDTO } from "../../../model/editarr-administrador.dto";

@Component({
    selector: "app-editar-administrador",
    templateUrl: "./administrador.component.html",
    styleUrls: ["./administrador.component.css"],
    standalone: true,
    imports: [
        CommonModule, 
        RouterModule,
        FormsModule
    ]
})
export class EditarAdministradorComponent {

    admi: EditarAdministradorDTO = {
        nombre: '',
        apellido: '',
        email: '',
        password: '',
        telefono: ''
    };

    rol: string | null = null;
    passwordConfirm: string = '';
    passwordError: string = "";

    constructor(private usuarioService: UsuarioService, private authService: AuthService, private router: Router) {}
    
    ngOnInit() {
        this.rol = this.authService.getRol();
    }

    cargarDatos() {
        this.usuarioService.getAdministrador().subscribe({
            next: (data) => {
                Object.assign(this.admi, data);
                console.log('Administrador cargado:', this.admi);
            },
            error: (err) => {
                console.error("Error al cargar los datos del administrador:", err);
            }
        });
    }

    guardarDatos() {
        if (this.passwordError) {
            return;
        }

        

    }

    paginaEmpresa(): void {
        if (this.rol === 'ADMINISTRADOR') {
        this.router.navigate(['/empresa']);
        } else if (this.rol === 'VENDEDOR') {
        this.irEmpresa();
        }
    }

    irEmpresa(): void {

    }

    logout() {
        this.authService.logout();
        this.router.navigate(['/login']);
    }

    tieneMinimo(password: string) {
        return password?.length >= 8;
    }

    tieneMayuscula(password: string) {
        return /[A-Z]/.test(password || '');
    }

    tieneMinuscula(password: string) {
        return /[a-z]/.test(password || '');
    }

    tieneNumero(password: string) {
        return /[0-9]/.test(password || '');
    }

    tieneSimbolo(password: string) {
        return /[!@#$%^&*(),.?":{}|<>]/.test(password || '');
    }

}