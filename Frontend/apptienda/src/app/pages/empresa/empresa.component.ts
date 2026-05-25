import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

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

  constructor(private router: Router) {}

}