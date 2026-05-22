import { Component } from "@angular/core";
import { AuthService } from "../../services/auth.service";
import { UsuarioService } from "../../services/usuario.service";
import { FormsModule } from "@angular/forms";
import { Router } from "@angular/router";
import { RouterLink } from "@angular/router";
import { jwtDecode } from "jwt-decode";
import { CommonModule } from "@angular/common";



@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"],
  standalone: true,
  imports: [
    FormsModule, 
    RouterLink,
    CommonModule
  ]

})
export class LoginComponent {

  email: string = "";
  password: string = "";
  contadorErrores: number = 0;
  emailActual: string = "";
  mensaje: string = "";


  constructor(
    private authService: AuthService,
    private usuarioService: UsuarioService,
    private router: Router
  ) {}

  onLogin() {
    this.mensaje = "";

    const usuario = {
      email: this.email,
      password: this.password
    };

    this.authService.login(usuario).subscribe({
      
      next: (res: any) => {

        this.mensaje = "";

        this.authService.guardarToken(res.token);

        const decoded: any = jwtDecode(res.token);
        const rol = decoded.rol;

        this.router.navigate(['/inicio']);
      },

      error: (error) => {

        if (this.emailActual !== this.email) {
          this.emailActual = this.email;
          this.contadorErrores = 0; // Se reiniciara el contador cada vez que se cambie de email
        } 

        this.contadorErrores++;
        this.mensaje = "Credenciales incorrectas";
        console.log("Intentos fallidos: ", this.contadorErrores);
            console.log("Mensaje: " + this.mensaje);

        if (this.contadorErrores === 3) {
          this.usuarioService.bloquearUsuario(this.email).subscribe({
          next: (res: any) => {
            console.log("Usuario bloqueado", res.mensaje);
            this.mensaje = res.mensaje;
          },
          error: (err) => {
            console.error("Error al bloquear usuario", err);
            this.mensaje = "Error al bloquear usuario. Por favor, intente nuevamente.";
          }
        });
          return;
        }
      }
    });
  }
}