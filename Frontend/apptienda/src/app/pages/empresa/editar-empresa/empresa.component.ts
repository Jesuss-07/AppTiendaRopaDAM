import { Component } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { EmpresaService } from '../../../services/empresa.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ActualizarEmpresaDTO } from '../../../model/actualizar-empresa.dto';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-empresa',
    templateUrl: './empresa.component.html',
    styleUrl: './empresa.component.css',
    standalone: true,
    imports: [
        CommonModule,
        FormsModule
    ]
})
export class EditarEmpresaComponent {

    rol: string | null = null;
    idEmpresa: number = 0;
    empresa: ActualizarEmpresaDTO = {
        nombreEmpresa: '',
        emailContacto: '',
        telefonoContacto: '',
        direccionSede: '',
        logoEmpresa: ''
    };

    constructor(private authService: AuthService, private empresaService: EmpresaService, private router: Router, private route: ActivatedRoute) { }

    ngOnInit() {
        this.rol = this.authService.getRol();
        this.idEmpresa = Number(this.route.snapshot.paramMap.get('id'));
        console.log('ID de la empresa:', this.idEmpresa);

        this.empresaService.obtenerEmpresaPorId(this.idEmpresa).subscribe({
            next: (data) => { this.empresa = data; },
            error: (err) => { console.error('Error al obtener la empresa:', err); }
        });

    }

    actualizarEmpresa() {
        this.empresaService.actualizarEmpresa(this.idEmpresa, this.empresa).subscribe({
            next: () => {
                this.router.navigate(['/empresa']);
            },
            error: (err) => {
                console.error('Error al actualizar la empresa:', err);
            }
        });
    }

    logout() {
        this.authService.logout();
        this.router.navigate(['/login']);
    }

}