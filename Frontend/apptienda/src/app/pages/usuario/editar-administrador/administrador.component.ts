import { Component } from "@angular/core";
import { CommonModule } from "@angular/common";
import { UsuarioService } from "../../../services/usuario.service";
import { AuthService } from "../../../services/auth.service";
import { Router, RouterModule } from "@angular/router";
import { FormsModule } from "@angular/forms";
import { EditarAdministradorDTO } from "../../../model/editarr-administrador.dto";

@Component({
  selector: "app-editar-administrador",
  templateUrl: "./administrador.component.html",
  styleUrls: ["./administrador.component.css"],
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule]
})
export class EditarAdministradorComponent {

  admin: EditarAdministradorDTO = {
    nombre: '',
    apellido: '',
    email: '',
    telefono: '',
    password: ''
  };

  rol: string | null = null;

  password: string = "";
  passwordConfirm: string = "";
  passwordError: string = "";

  constructor(
    private usuarioService: UsuarioService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.rol = this.authService.getRol();
    this.cargarDatos();
  }

  cargarDatos() {
    this.usuarioService.getAdministrador().subscribe({
      next: (data) => {
          console.log("ADMIN RAW:", data);
        Object.assign(this.admin, data);
      },
      error: (err) => {
        console.error("Error al cargar admin:", err);
      }
    });
  }

  guardarDatos() {

    if (this.passwordError) return;

    const dto: any = {
      nombre: this.admin.nombre,
      apellido: this.admin.apellido,
      email: this.admin.email,
      telefono: this.admin.telefono
    };

    // solo enviar password si realmente se escribió
    if (this.password && this.password.length > 0) {
      dto.password = this.password;
    }

    this.usuarioService.updateAdministrador(dto).subscribe({
      next: () => {
        alert("Administrador actualizado correctamente");
        this.router.navigate(['/inicio']);
      },
      error: (err) => {
        console.error(err);
        alert("Error al guardar");
      }
    });
  }

  validarPassword() {

    if (!this.password && !this.passwordConfirm) {
      this.passwordError = "";
      return;
    }

    if (this.password !== this.passwordConfirm) {
      this.passwordError = "Las contraseñas no coinciden";
      return;
    }

    const pass = this.password;

    const valid =
      this.tieneMinimo(pass) &&
      this.tieneMayuscula(pass) &&
      this.tieneMinuscula(pass) &&
      this.tieneNumero(pass) &&
      this.tieneSimbolo(pass);

    if (!valid) {
      this.passwordError = "La contraseña no cumple los requisitos";
      return;
    }

    this.passwordError = "";
  }

  paginaEmpresa(): void {
    if (this.rol === 'ADMINISTRADOR') {
      this.router.navigate(['/empresa']);
    } else if (this.rol === 'VENDEDOR') {
      this.irEmpresa();
    }
  }

  irEmpresa(): void {}

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  tieneMinimo(p: string) {
    return (p || '').length >= 8;
  }

  tieneMayuscula(p: string) {
    return /[A-Z]/.test(p || '');
  }

  tieneMinuscula(p: string) {
    return /[a-z]/.test(p || '');
  }

  tieneNumero(p: string) {
    return /[0-9]/.test(p || '');
  }

  tieneSimbolo(p: string) {
    return /[!@#$%^&*(),.?":{}|<>]/.test(p || '');
  }
}