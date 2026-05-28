import { Talla } from "./talla.enum";
export interface CrearProductoDTO {

    nombreProducto: string;
    descripcion: string;
    precio: number;
    stock: number;
    talla: Talla;    
    color: string;
    categoria: string;
    imagenProducto: string;

}