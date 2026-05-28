package com.iesagora.jesus.apptienda.business.services;

import java.util.List;

import com.iesagora.jesus.apptienda.business.dto.CrearProductoDTO;
import com.iesagora.jesus.apptienda.business.dto.EditarProductoDTO;
import com.iesagora.jesus.apptienda.business.dto.ListarProductoDTO;

public interface ProductoServices {
	
	void crearProducto(CrearProductoDTO productoDTO);
	
	void eliminarProducto(Long id);
	
	EditarProductoDTO editarProducto(Long id, EditarProductoDTO productoDTO);
	
	List<ListarProductoDTO> listarProductosPorVendedor(Long id);

}
