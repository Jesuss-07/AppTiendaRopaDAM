import { ProductoCarritoDTO } from "./producto-carrito.dto";

export interface DetallePedidoDTO {
    productoCarritoDTO: ProductoCarritoDTO;
    cantidad: number;
    precioUnidad: number;
    subtotal: number;
}