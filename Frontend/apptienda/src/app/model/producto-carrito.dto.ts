import { CategoriaRopa } from "./categoria.enum";
import { Talla } from "./talla.enum";

export interface ProductoCarritoDTO {
    nombreProducto: string;
    talla: Talla;
    color: string;
    categoria: CategoriaRopa;
    imagenProducto: string;
}