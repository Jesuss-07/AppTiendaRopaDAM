import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-empresa',
  templateUrl: './empresa.component.html',
    styleUrls: ['./empresa.component.css'],
    standalone: true,
    imports: [
        RouterLink,
        CommonModule
    ]
})

export class EmpresaComponent {

  constructor(private router: Router, private authService: AuthService) {}

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}