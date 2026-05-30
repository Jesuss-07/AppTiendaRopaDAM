package com.iesagora.jesus.apptienda.business.services;

import com.iesagora.jesus.apptienda.business.dto.PedidoDTO;

public interface PedidoServices {
	
	void crearPedido(Long idUsuario);
	
	PedidoDTO mostrarPedido(Long idUsuario);
	
	void finalizarPedido(Long idUsuario);

}
