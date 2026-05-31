import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarritoService } from '../../services/carrito.service';
import { DetallePedidoDTO } from '../../model/detalle-pedido.dto';
import { PedidoDTO } from '../../model/pedido.dto';
import { ProductoCarritoDTO } from '../../model/producto-carrito.dto';
import { Router } from '@angular/router';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({ 
    selector: 'app-carrito',
    templateUrl: './carrito.component.html',
    styleUrl: './carrito.component.css',
    standalone: true,
    imports: [
        CommonModule,
        RouterLink
    ]
})
export class CarritoComponent {

    rol: string | null = null;

    constructor(private carritoService: CarritoService, private router: Router, private authService: AuthService) {}

    ngOnInit() {
        this.rol = this.authService.getRol();
        console.log('Rol del usuario:', this.rol);
    }

    logout() {
        this.authService.logout();
        this.router.navigate(['/login']);
    }

}