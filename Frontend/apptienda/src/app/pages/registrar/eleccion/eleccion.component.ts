import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { RouterModule } from "@angular/router";

@Component({
    selector: "app-eleccion-registro",
    templateUrl: "./eleccion.component.html",
    styleUrls: ["./eleccion.component.css"],
    standalone: true,
    imports: [
        RouterModule
    ]
})
export class EleccionComponent {}