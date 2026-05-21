import { Component } from "@angular/core";
import { Route, Router } from "@angular/router";

@Component({
    selector: "app-eleccion-registro",
    templateUrl: "./eleccion.component.html",
    styleUrls: ["./eleccion.component.css"],
    standalone: true,
    imports: []
})
export class EleccionComponent {
    constructor(private router: Router) {}

    registrarCliente() {
        this.router.navigate(['/registro/cliente']);
    }

    registrarVendedor() {
        this.router.navigate(['/registro/vendedor']);
    }

}