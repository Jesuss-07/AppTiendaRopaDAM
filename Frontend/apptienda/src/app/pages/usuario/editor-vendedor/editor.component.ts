import { UsuarioService } from "../../../services/usuario.service";
import { Component } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Router } from "@angular/router";
import { CommonModule } from "@angular/common";

@Component({
  selector: "app-editor-vendedor",
  templateUrl: "./editor.component.html",
    styleUrls: ["./editor.component.css"],
    standalone: true,
    imports: [
      FormsModule,
      CommonModule
    ]
})
export class EditorVendedorComponent {
    
}