import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../core/config/environment';
import { PedidoDTO } from '../model/pedido.dto';

@Injectable({
  providedIn: 'root'
})
export class CarritoService {
    private pedidoUrl = environment.pedidoUrl;
    private detallePedidoUrl = environment.detallePedidoUrl;

  constructor(private http: HttpClient ) {}

    pagarPedido() {
        return this.http.post(`${this.pedidoUrl}/pagar`, {});
    }

    listarCarrito() {
      return this.http.get<PedidoDTO>(`${this.pedidoUrl}/listar`);
    }

    anadirProducto(idProducto: number, cantidad: number) {
      return this.http.post(`${this.detallePedidoUrl}/anadir?idProducto=${idProducto}&cantidad=${cantidad}`,{});
    }

    eliminarProducto(idDetallePedido: number) {
      return this.http.delete(`${this.detallePedidoUrl}/eliminar?idDetallePedido=${idDetallePedido}`);
    }

    actualizarCantidad(idDetallePedido: number, cantidad: number) {
      return this.http.put(`${this.detallePedidoUrl}/actualizar?idDetallePedido=${idDetallePedido}&cantidad=${cantidad}`, {});
    }

}