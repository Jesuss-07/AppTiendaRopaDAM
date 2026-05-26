import { Component } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { EmpresaService } from '../../../services/empresa.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-empresa',
    templateUrl: './empresa.component.html',
    styleUrl: './empresa.component.css',
    standalone: true,
    imports: [
        CommonModule
    ]
})
export class EditarEmpresaComponent {

    rol: string | null = null;

    constructor(private authService: AuthService, private empresaService: EmpresaService, private router: Router) { }

    ngOnInit() {
        this.rol = this.authService.getRol();
    }

    logout() {
        this.authService.logout();
        this.router.navigate(['/login']);
    }

}