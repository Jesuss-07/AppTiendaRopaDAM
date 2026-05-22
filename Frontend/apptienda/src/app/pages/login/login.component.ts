import { Component } from "@angular/core";
import { AuthService } from "../../services/auth.service";
import { FormsModule } from "@angular/forms";
import { Router } from "@angular/router";
import { RouterLink } from "@angular/router";
import { jwtDecode } from "jwt-decode";



@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"],
  standalone: true,
  imports: [
    FormsModule, 
    RouterLink
  ]

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
            console.log("Usuario:", usuario);


    this.authService.login(usuario).subscribe({
      
      next: (res: any) => {

        console.log("Login correcto", res);

        this.authService.guardarToken(res.token);

        const decoded: any = jwtDecode(res.token);
        const rol = decoded.rol;

        this.router.navigate(['/inicio']);
      },

      error: (error) => {
        console.log("Usuario:", this.email);
        console.log("Password:", this.password);
        console.log("Error en login", error);
      }
    });
  }
}