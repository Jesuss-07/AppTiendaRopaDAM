import { Component } from "@angular/core";
import { AuthService } from "../../services/auth.service";
import { FormsModule } from "@angular/forms";
import { Router } from "@angular/router";



@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"],
  standalone: true,
  imports: [FormsModule]




})
export class LoginComponent {

  email: string = "";
  password: string = "";


  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onLogin() {

    const usuario = {
      email: this.email,
      password: this.password
    };


    this.authService.login(usuario).subscribe({
      next: (res: any) => {

        console.log("Login correcto", res);

        this.authService.guardarToken(res.token);

        const rol = this.authService.getRol();
        console.log("Rol:", rol);

        if (rol === "CLIENTE") {
          this.router.navigate(['/cliente']);
        }else if (rol === "VENDEDOR") {
          this.router.navigate(['/vendedor']);
        }
      },

      error: (error) => {
        console.log("Error en login", error);
            console.log("Usuario:", usuario);
      }
    });
  }
}