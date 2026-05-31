export interface EditarProductoDTO {
    idProducto?: number;
    nombreProducto: string;
    descripcion: string;
    precio: number;
    stock: number;
    talla: string;
    color: string;
    categoria: string;
    imagenProducto: string;
}