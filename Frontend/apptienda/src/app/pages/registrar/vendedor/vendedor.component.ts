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
}
