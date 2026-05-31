import { ProductoCarritoDTO } from "./producto-carrito.dto";

export interface DetallePedidoDTO {
    productoCarritoDTO: ProductoCarritoDTO;
    cantidad: number;
    precioUnitario: number;
    subtotal: number;
}