import { Component } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { EmpresaService } from "../../../services/empresa.service";
import { Router } from "@angular/router";
import { AuthService } from "../../../services/auth.service";
import { RouterModule } from "@angular/router";

@Component({
    selector: "app-empresa",
    standalone: true,
    imports: [
        CommonModule,
        FormsModule,
        RouterModule
    ],
    templateUrl: "./empresa.component.html",
    styleUrl: "./empresa.component.css",
})

export class RegistroEmpresaComponent {

    empresa = {
        nombreEmpresa: '',
        cif: '',
        emailContacto: '',
        telefonoContacto: '',
        direccionSede: '',
        logoEmpresa: ''   
    };

    rol: string | null = null;

    constructor(private empresaService: EmpresaService, private router: Router, private authService: AuthService) {}

    ngOnInit() {
        this.rol = this.authService.getRol();
        console.log('Rol del usuario:', this.rol);
    }

    onRegistroEmpresa() {
        this.empresaService.registroEmpresa(this.empresa).subscribe({
            next: (res) => {
                console.log('Registro exitoso:', res);
                this.router.navigate(['/inicio']); // Redirige a la página de empresa después del registro exitoso
            },
            error: (err) => {
                console.error('Error en el registro:', err);
            }
        });
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

    logout() {
        localStorage.removeItem('token');
        this.router.navigate(['/login']);
    }
}