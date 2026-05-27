import { UsuarioService } from "../../../services/usuario.service";
import { Component } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Router } from "@angular/router";
import { CommonModule } from "@angular/common";
import { AuthService } from "../../../services/auth.service";
import { RouterModule } from "@angular/router";
@Component({
  selector: "app-editor-cliente",
  templateUrl: "./editor.component.html",
    styleUrls: ["./editor.component.css"],
    standalone: true,
    imports: [
      FormsModule,
      CommonModule,
      RouterModule
    ]
})
export class EditorClienteComponent {

  cliente: any = {
    nombre: '',
    apellido: '',
    email: '',
    password: '',
    passwordConfirm: '',
    telefono: '',
    direccion1: '',
    direccion2: '',
    cp: '',
    pais: '',
    ciudad: '',
    provincia: ''
  };

  rol: string | null = null;
  passwordError: string = "";

  constructor(private usuarioService: UsuarioService, private router: Router, private authServices: AuthService) {}

  ngOnInit() {
    this.cargarDatos();
    this.rol = this.authServices.getRol();
  }

  cargarDatos() {
    this.usuarioService.getCliente().subscribe({
      next: (data) => {
        Object.assign(this.cliente, data);
        console.log('Cliente cargado:', this.cliente);
      },
      error: (err) => {
        console.error("Error al cargar los datos del cliente:", err);
      }
    });
  }

  guardarDatos() {

  if (this.passwordError) 
    return;

  const dto: any = {
    nombre: this.cliente.nombre ?? "",
    apellido: this.cliente.apellido ?? "",
    email: this.cliente.email ?? "",
    telefono: this.cliente.telefono ?? "",
    direccion1: this.cliente.direccion1 ?? "",
    direccion2: this.cliente.direccion2 ?? "",
    cp: this.cliente.cp ?? "",
    pais: this.cliente.pais ?? "",
    ciudad: this.cliente.ciudad ?? "",
    provincia: this.cliente.provincia ?? ""
  };

  if (this.cliente.password) {
    dto.password = this.cliente.password;
  }

  this.usuarioService.updateCliente(dto).subscribe({
    next: () => {
      alert("Datos guardados correctamente");
      this.router.navigate(['/inicio']);
    },
    error: (err) => {
      console.error("Error al guardar los datos del cliente:", err);
      alert("Error al guardar los datos. Por favor, inténtalo de nuevo.");
    }
  });
}



  editarUsuario() {
    if(this.rol === 'VENDEDOR') {
      this.router.navigate(['/editor/vendedor']);
    } else if(this.rol === 'CLIENTE') {
      this.router.navigate(['/editor/cliente']);
    }
  }

  logout() {
    this.authServices.logout();
    this.router.navigate(['/login']);
  }
  
  validarPassword() {
    const pass = this.cliente.password || "";
    const confirm = this.cliente.passwordConfirm || "";

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
}