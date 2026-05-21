import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { FormsModule } from "@angular/forms";
import { AuthService } from "../../../services/auth.service";

@Component({
    selector: "app-vendedor",
    templateUrl: "./vendedor.component.html",
    styleUrls: ["./vendedor.component.css"],
    imports: [FormsModule],
    standalone: true
})
export class VendedorComponent {

    vendedor = {
        cif: '',
        nombre: '',
        apellido: '',
        email: '',
        password: '',
        numeroEmpleado: ''
    };

    constructor(private authService: AuthService, private router: Router) {}

    onRegistroVendedor() {
        this.authService.registroVendedor(this.vendedor).subscribe({
            next: (res) => {
                console.log('Registro exitoso:', res);
                this.router.navigate(['/login']);
            },
            error: (err) => {
                console.error('Error en el registro:', err);
            }
        });
    }

    validarPassword(password: string = ''): boolean {
        const minimoOcho = password.length >= 8;
        const tieneNumero = /[0-9]/.test(password);
        const tieneMayuscula = /[A-Z]/.test(password);
        const tieneMinuscula = /[a-z]/.test(password);
        const tieneSimbolo = /[!@#$%^&*(),.?":{}|<>]/.test(password);

        return minimoOcho && tieneNumero && tieneMayuscula && tieneMinuscula && tieneSimbolo;
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
