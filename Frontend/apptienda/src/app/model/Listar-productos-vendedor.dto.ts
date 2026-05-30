export interface ListarProductosVendedorDTO {
    idProducto: number;
    nombreProducto: string;
    precio: number;
    stock: number;
    talla: string;
    color: string;
    descripcion?: string;
    categoria: string;
    imagenProducto: string;
}