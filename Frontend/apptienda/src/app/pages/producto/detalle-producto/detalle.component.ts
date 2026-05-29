import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductoService } from '../../../services/producto.service';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-detalle',
  templateUrl: './detalle.component.html',
    styleUrl: './detalle.component.css',
    standalone: true,
    imports: []
})

export class DetalleComponent {

    rol: string | null = null;

    constructor(private route: ActivatedRoute, private productoService: ProductoService, private authService: AuthService) {}

    ngOnInit(): void {}

}