import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { FormsModule } from "@angular/forms";
import { AuthService } from "../../../services/auth.service";

@Component({
    selector: "app-cliente",
    templateUrl: "./cliente.component.html",
    styleUrl: "./cliente.component.css",
    imports: [FormsModule],
    standalone: true
})

export class ClienteComponent {

    cliente = {
        nombre: '',
        apellido: '',
        email: '',
        password: '',
        telefono: '',
        direccion1: '',
        direccion2: '',
        cp: '',
        pais: '',
        ciudad: '',
        provincia: ''
    };

    constructor(private authService: AuthService, private router: Router) {}

    onRegistroCliente() {

        if(!this.validarPassword(this.cliente.password)) {
            alert("La contraseña no cumple los requisitos:\n- 8 caracteres\n- Mayúscula y minúscula\n- Número\n- Carácter especial");
            return;
        }

        this.authService.registroCliente(this.cliente).subscribe({
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
        const tieneLetraMayuscula = /[A-Z]/.test(password);
        const tieneLetraMinuscula = /[a-z]/.test(password);
        const tieneCaracterEspecial = /[!@#$%^&*(),.?":{}|<>]/.test(password);

        return minimoOcho && tieneNumero && tieneLetraMayuscula && tieneLetraMinuscula && tieneCaracterEspecial;
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