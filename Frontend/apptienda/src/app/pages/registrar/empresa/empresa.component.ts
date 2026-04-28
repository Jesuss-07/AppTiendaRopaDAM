import { Component } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { EmpresaService } from "../../../services/empresa.service";
import { Router } from "@angular/router";

@Component({
    selector: "app-empresa",
    standalone: true,
    imports: [FormsModule],
    templateUrl: "./empresa.component.html",
    styleUrl: "./empresa.component.css",
})

export class EmpresaComponent {

    empresa = {
        nombreEmpresa: '',
        cif: '',
        emailContacto: '',
        telefonoContacto: '',
        direccionSede: '',
        logoEmpresa: ''   
    };

    constructor(private empresaService: EmpresaService, private router: Router) {}

    onRegistroEmpresa() {
        this.empresaService.registroEmpresa(this.empresa).subscribe({
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