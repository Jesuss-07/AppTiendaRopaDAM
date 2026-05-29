package com.iesagora.jesus.apptienda.business.services;

import java.util.List;

import com.iesagora.jesus.apptienda.business.dto.ProductoDTO;
import com.iesagora.jesus.apptienda.business.dto.EditarProductoDTO;
import com.iesagora.jesus.apptienda.business.dto.ListarProductoDTO;

public interface ProductoServices {
	
	void crearProducto(ProductoDTO productoDTO);
	
	void eliminarProducto(Long id);
	
	ProductoDTO obtenerProductoDTO(Long id);
		                     
	EditarProductoDTO editarProducto(Long id, EditarProductoDTO productoDTO);
	
	List<ListarProductoDTO> listarProductosPorVendedor(Long id);
	
	List<ListarProductoDTO> listarProductos();

}
