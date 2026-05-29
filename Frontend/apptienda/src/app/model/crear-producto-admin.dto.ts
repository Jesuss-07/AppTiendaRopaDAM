import { Talla } from "./talla.enum";
import { CategoriaRopa } from "./categoria.enum";

export interface CrearProductoAdminDTO {

    nombreProducto: string;
    descripcion: string;
    precio: number;
    stock: number;
    talla: Talla;    
    color: string;
    categoria: CategoriaRopa;
    imagenProducto: string;
    cifEmpresa: string;

}