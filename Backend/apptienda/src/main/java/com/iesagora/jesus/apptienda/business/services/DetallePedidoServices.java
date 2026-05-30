package com.iesagora.jesus.apptienda.business.services;

public interface DetallePedidoServices {
	
	void agregarProducto(Long idUsuario, Long idProducto,int cantidad);
	
	void eliminarProducto(Long idDetalleProducto);
	
	void actualizarCantidad(Long idDetalleProducto, int cantidad);
	

}
