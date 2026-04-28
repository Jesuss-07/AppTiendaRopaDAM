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
}