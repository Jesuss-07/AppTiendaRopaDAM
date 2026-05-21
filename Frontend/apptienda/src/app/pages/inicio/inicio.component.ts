import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
    styleUrls: ['./inicio.component.css'],
    standalone: true,
    imports: [
        RouterLink
    ]
})
export class InicioComponent {}