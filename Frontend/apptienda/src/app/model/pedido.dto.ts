import { DetallePedidoDTO } from "./detalle-pedido.dto";

export interface PedidoDTO {
    detallePedidoDTO: DetallePedidoDTO[];
    total: number;
    direccionCliente: string;
    cpCliente: string;
    ciudadCliente: string;
    provinciaCliente: string;
    paisCliente: string;
}