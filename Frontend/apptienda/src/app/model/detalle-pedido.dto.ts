import { ProductoCarritoDTO } from "./producto-carrito.dto";

export interface DetallePedidoDTO {
    idDetallePedidoDTO: number;
    productoCarritoDTO: ProductoCarritoDTO;
    cantidad: number;
    precioUnidad: number;
    subtotal: number;
}