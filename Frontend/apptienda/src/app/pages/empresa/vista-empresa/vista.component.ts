import { Component } from "@angular/core";
import { CommonModule } from "@angular/common";
import { EmpresaService } from "../../../services/empresa.service";
import { UsuarioService } from "../../../services/usuario.service";
import { AuthService } from "../../../services/auth.service";
import { ActivatedRoute, Router, RouterModule } from "@angular/router";
import { switchMap, tap } from "rxjs/operators";
import { VistaEmpresaDTO } from "../../../model/vista-empresa.dto";
import { VistaVendedorDTO } from "../../../model/vista-vendedor.dto";

@Component({
    selector: "app-vista-empresa",
    templateUrl: "./vista.component.html",
    styleUrls: ["./vista.component.css"],
    standalone: true,
    imports: [
        CommonModule,
         RouterModule
    ]
})
export class VistaEmpresaComponent {

    empresa: VistaEmpresaDTO | null = null;
    vendedores: VistaVendedorDTO[] = [];

    rol: string | null = null;

    loading = true;
    bloqueandoEmail: string | null = null;

    isAdmin = false;

    constructor(
        private empresaService: EmpresaService,
        private usuarioService: UsuarioService,
        private authService: AuthService,
        private route: ActivatedRoute,
        private router: Router
    ) {}

    ngOnInit() {

        this.rol = this.authService.getRol();
        this.isAdmin = this.rol === 'ADMINISTRADOR';

        this.loading = true;

        this.route.paramMap.pipe(

            switchMap(params => {
                const id = Number(params.get('id'));
                return this.empresaService.obtenerVistaEmpresa(id);
            }),

            tap(empresaData => {
                console.log('EMPRESA:', empresaData);
                this.empresa = empresaData;
            }),

            switchMap(empresaData => {

                if (!empresaData?.cif) {
                    console.error('ERROR: CIF NO EXISTE');
                    return [];
                }

                return this.usuarioService.obtenerVendedoresPorEmpresa(
                    empresaData.cif
                );
            })

        ).subscribe({

            next: (vendedoresData: any) => {
                this.vendedores = vendedoresData ?? [];
                this.loading = false;
            },

            error: (err) => {
                console.error('ERROR GLOBAL:', err);
                this.loading = false;
            },

            complete: () => {
                this.loading = false;
            }

        });
    }

    bloquearVendedor(email: string): void {

        if (!confirm('¿Estás seguro de bloquear este vendedor?')) return;
        this.bloqueandoEmail = email;
        this.usuarioService.bloquearUsuario(email).subscribe({
            next: () => {
                this.vendedores = this.vendedores.filter(
                    v => v.emailCorporativo !== email
                );
                this.bloqueandoEmail = null;
            },
            error: (err) => {
                console.error(err);
                this.bloqueandoEmail = null;
            }
        });
    }

    editarVendedor(email: string) {
        this.router.navigate(['/editar/vendedor', email]);
    }

    editarUser(): void {

        if (this.rol === 'ADMINISTRADOR') {
            this.router.navigate(['/editor/administrador']);
        }
        if (this.rol === 'VENDEDOR') {
            this.router.navigate(['/editor/vendedor']);
        }
        if (this.rol === 'CLIENTE') {
            this.router.navigate(['/editor/cliente']);
        }
    }

    logout() {
        this.authService.logout();
        this.router.navigate(['/login']);
    }
}