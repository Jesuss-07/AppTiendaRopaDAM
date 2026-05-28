package com.iesagora.jesus.apptienda.business.services;

import com.iesagora.jesus.apptienda.business.dto.CrearProductoDTO;
import com.iesagora.jesus.apptienda.business.dto.EditarProductoDTO;

public interface ProductoServices {
	
	void crearProducto(CrearProductoDTO productoDTO);
	
	void eliminarProducto(Long id);
	
	EditarProductoDTO editarProducto();

}
