import { Component } from "@angular/core";
import { CommonModule } from "@angular/common";
import { EmpresaService } from "../../../services/empresa.service";
import { UsuarioService } from "../../../services/usuario.service";
import { AuthService } from "../../../services/auth.service";
import { ActivatedRoute, Router, RouterModule } from "@angular/router";
import { switchMap, tap, map } from "rxjs/operators";
import { of, Observable } from "rxjs";

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

  rol: string | null = null;
  isAdmin = false;

  bloqueandoEmail: string | null = null;

  constructor(
    private empresaService: EmpresaService,
    private usuarioService: UsuarioService,
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  empresa$!: Observable<VistaEmpresaDTO>;
  vendedores$!: Observable<VistaVendedorDTO[]>;

  ngOnInit() {

    this.rol = this.authService.getRol();
    this.isAdmin = this.rol === 'ADMINISTRADOR';

    console.log('ROL:', this.rol);

    this.empresa$ = this.route.paramMap.pipe(
      switchMap(params => {
        const id = Number(params.get('id'));
        return this.empresaService.obtenerVistaEmpresa(id);
      }),
      tap(empresa => console.log('EMPRESA:', empresa))
    );

    this.vendedores$ = this.empresa$.pipe(
      switchMap(empresa => {

        if (!empresa?.cif) {
          return of([]);
        }

        return this.usuarioService.obtenerVendedoresPorEmpresa(empresa.cif);
      })
    );
  }

  bloquearVendedor(email: string): void {

    if (!confirm('¿Estás seguro de bloquear este vendedor?')) return;

    this.bloqueandoEmail = email;

    this.usuarioService.bloquearUsuario(email).subscribe({
      next: () => {
        console.log('Vendedor bloqueado:', email);
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
    } else if (this.rol === 'VENDEDOR') {
      this.router.navigate(['/editor/vendedor']);
    } else if (this.rol === 'CLIENTE') {
      this.router.navigate(['/editor/cliente']);
    }
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}