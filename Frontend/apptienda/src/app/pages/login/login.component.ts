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

  const usuario = {
    email: this.email,
    password: this.password
  };

  this.authService.login(usuario).subscribe({

    next: (res:any) => {

      this.mensaje = "";

      this.authService.guardarToken(res.token);

      this.router.navigate(['/inicio']);
    },

    error: (error) => {

      this.contadorErrores++;
      this.mensaje = "Credenciales incorrectas";

      if(this.contadorErrores === 3){

        this.usuarioService.bloquearUsuario(this.email).subscribe({
          next:(res:any)=>{
            this.mensaje = res.mensaje;
          }
        });
      }
    }
  });
}
}