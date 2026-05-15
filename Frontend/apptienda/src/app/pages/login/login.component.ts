import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';


import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [
    FormsModule
  ]
})
export class LoginComponent {

  email: string = "";
  password: string = "";
  mensajeError: string = "";

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
        this.authService.guardarToken(res.token);
        const rol = this.authService.getRol();

        if (rol === "CLIENTE") {
          this.router.navigate(['/cliente']);
        } else if (rol === "VENDEDOR") {
          this.router.navigate(['/empresa']);
        } else {
          this.router.navigate(['/']);
        }
      },
      error: (error) => {
        console.log("Error en login", error);
        this.mensajeError = "Credenciales incorrectas";
      }
    });
  }
}
