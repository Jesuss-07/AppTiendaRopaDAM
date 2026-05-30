package com.iesagora.jesus.apptienda.business.services;

import com.iesagora.jesus.apptienda.business.model.Pedido;

public interface PedidoServices {
	
	void crearPedido(Long idUsuario);
	
	Pedido mostrarPedido(Long idUsuario);
	
	void finalizarPedido(Long idUsuario);

}
