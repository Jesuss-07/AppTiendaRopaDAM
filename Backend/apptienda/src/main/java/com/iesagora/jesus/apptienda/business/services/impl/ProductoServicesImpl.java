package com.iesagora.jesus.apptienda.business.services.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.iesagora.jesus.apptienda.business.dto.CrearProductoDTO;
import com.iesagora.jesus.apptienda.business.dto.EditarProductoDTO;
import com.iesagora.jesus.apptienda.business.model.Producto;
import com.iesagora.jesus.apptienda.business.model.Usuario;
import com.iesagora.jesus.apptienda.business.model.Vendedor;
import com.iesagora.jesus.apptienda.business.repositories.ProductoRepository;
import com.iesagora.jesus.apptienda.business.services.ProductoServices;

@Service
public class ProductoServicesImpl implements ProductoServices{
	
	private final ProductoRepository productoRepository;
	
	public ProductoServicesImpl(ProductoRepository productoRepository) {
		this.productoRepository = productoRepository;
	}

	@Override
	public void crearProducto(CrearProductoDTO productoDTO) {
		
 		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = (Usuario) auth.getPrincipal();		

	    if (!(usuario instanceof Vendedor vendedor)) {
	        throw new IllegalStateException("No es un vendedor");
	    }		
	    		
		Producto producto = new Producto();

        producto.setEmpresa(vendedor.getEmpresa());
        producto.setNombreProducto(productoDTO.getNombreProducto());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        producto.setTalla(productoDTO.getTalla());
        producto.setColor(productoDTO.getColor());
        producto.setCategoria(productoDTO.getCategoria());
        producto.setImagenProducto(productoDTO.getImagenProducto());	
		
        productoRepository.save(producto);
	}

	@Override
	public void eliminarProducto(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EditarProductoDTO editarProducto() {
		// TODO Auto-generated method stub
		return null;
	}

}


