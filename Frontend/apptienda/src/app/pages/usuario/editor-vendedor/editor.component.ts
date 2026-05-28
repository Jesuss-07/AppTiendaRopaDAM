import { UsuarioService } from "../../../services/usuario.service";
import { Component } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Router } from "@angular/router";
import { CommonModule } from "@angular/common";
import { AuthService } from "../../../services/auth.service";
import { RouterModule } from "@angular/router";

@Component({
  selector: "app-editor-vendedor",
  templateUrl: "./editor.component.html",
    styleUrls: ["./editor.component.css"],
    standalone: true,
    imports: [
      FormsModule,
      CommonModule,
      RouterModule
    ]
})
export class EditorVendedorComponent {

  vendedor: any = {
    nombre: "",
    apellido: "",
    email: "",
    password: "",
  };

  rol: string | null = null;
  passwordError: string = "";

  constructor(private usuarioService: UsuarioService, private router: Router, private authServices: AuthService) {}

  ngOnInit() {
    this.cargarDatos();
    this.rol = this.authServices.getRol();
  }

  cargarDatos() {
    this.usuarioService.getVendedor().subscribe({
      next: (data) => {
        Object.assign(this.vendedor, data);
        console.log('Vendedor cargado:', this.vendedor);
      },
      error: (err) => {
        console.error("Error al cargar los datos del vendedor:", err);
      }
    });
  }

  guardarDatos() {

    if (this.passwordError) 
      return;

    const dto = {
      nombre: this.vendedor.nombre ?? "",
      apellido: this.vendedor.apellido ?? "",
      email: this.vendedor.email ?? "",
      password: this.vendedor.password ?? ""
    };

    this.usuarioService.updateVendedor(dto).subscribe({
      next: (data) => {
        alert("Datos actualizados correctamente");
        this.router.navigate(['/inicio']);
      },
      error: (err) => {
        console.error("Error al actualizar los datos del vendedor:", err);
        alert("Error al actualizar los datos");
      }
    });
  }
  
  validarPassword() {
    const pass = this.vendedor.password || "";
    const confirm = this.vendedor.passwordConfirm || "";

    if (!pass && !confirm) {
      this.passwordError = "";
      return;
    }

    if (!pass && confirm) {
      this.passwordError = "Debes escribir una nueva contraseña";
      return;
    }

    const cumpleReglas =
      this.tieneMinimo(pass) &&
      this.tieneMayuscula(pass) &&
      this.tieneMinuscula(pass) &&
      this.tieneNumero(pass) &&
      this.tieneSimbolo(pass);

    if (!cumpleReglas) {
      this.passwordError = "La contraseña no cumple los requisitos mínimos";
      return;
    }

    if (pass !== confirm) {
      this.passwordError = "Las contraseñas no coinciden";
      return;
    }

    this.passwordError = "";
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

  logout() {
    this.authServices.logout();
    this.router.navigate(['/login']);
  }
  
}