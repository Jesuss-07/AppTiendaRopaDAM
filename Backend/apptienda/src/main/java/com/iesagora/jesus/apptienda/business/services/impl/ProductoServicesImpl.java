package com.iesagora.jesus.apptienda.business.services.impl;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.iesagora.jesus.apptienda.business.dto.ProductoDTO;
import com.iesagora.jesus.apptienda.business.dto.EditarProductoDTO;
import com.iesagora.jesus.apptienda.business.dto.ListarProductoDTO;
import com.iesagora.jesus.apptienda.business.dto.ProductoAdministradorDTO;
import com.iesagora.jesus.apptienda.business.model.Empresa;
import com.iesagora.jesus.apptienda.business.model.Producto;
import com.iesagora.jesus.apptienda.business.model.Usuario;
import com.iesagora.jesus.apptienda.business.model.Vendedor;
import com.iesagora.jesus.apptienda.business.repositories.EmpresaRepository;
import com.iesagora.jesus.apptienda.business.repositories.ProductoRepository;
import com.iesagora.jesus.apptienda.business.repositories.VendedorRepository;
import com.iesagora.jesus.apptienda.business.services.ProductoServices;

@Service
public class ProductoServicesImpl implements ProductoServices{
	
	private final ProductoRepository 	productoRepository;
	private final VendedorRepository 	vendedorRepository;
	private final EmpresaRepository 	empresaRepository;
	
	public ProductoServicesImpl(ProductoRepository productoRepository, VendedorRepository vendedorRepository, EmpresaRepository empresaRepository) {
		this.productoRepository = productoRepository;
		this.vendedorRepository = vendedorRepository;
		this.empresaRepository 	= empresaRepository;
	}

	@Override
	public void crearProducto(ProductoDTO productoDTO) {
		
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
	public void crearProductoAdmin(ProductoAdministradorDTO productoDTO) {
		
		Producto producto = new Producto();
		
		Empresa empresa = empresaRepository.findByCif(productoDTO.getCifEmpresa())
				.orElseThrow(() -> new IllegalStateException("No existe ninguna empresa con ese CIF"));

        producto.setEmpresa(empresa);
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
		Producto producto = productoRepository.findById(id)
				.orElseThrow(() -> new IllegalStateException("No se encuentra el producto con el id: " + id));
		
		productoRepository.delete(producto);
	}
	
	@Override
	public ProductoDTO obtenerProductoDTO(Long id) {
		Producto producto = productoRepository.findById(id)
				.orElseThrow(() -> new IllegalStateException("No se encuentra el producto con el id: " + id));
		
		ProductoDTO productoDTO = new ProductoDTO();
		
        productoDTO.setNombreProducto(producto.getNombreProducto());
        productoDTO.setDescripcion(producto.getDescripcion());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setStock(producto.getStock());
        productoDTO.setTalla(producto.getTalla());
        productoDTO.setColor(producto.getColor());
        productoDTO.setCategoria(producto.getCategoria());
        productoDTO.setImagenProducto(producto.getImagenProducto());	
		
		return productoDTO;
	}

	@Override
	public EditarProductoDTO editarProducto(Long id, EditarProductoDTO productoDTO) {
		Producto producto = obtenerProducto(id);
		
        producto.setNombreProducto(productoDTO.getNombreProducto());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        producto.setTalla(productoDTO.getTalla());
        producto.setColor(productoDTO.getColor());
        producto.setCategoria(productoDTO.getCategoria());
        producto.setImagenProducto(productoDTO.getImagenProducto());	
		
		productoRepository.save(producto);
        
		return productoDTO;
	}

	@Override
	public List<ListarProductoDTO> listarProductosPorVendedor(Long id) {
		
		Vendedor vendedor = vendedorRepository.findById(id)
				.orElseThrow(() -> new IllegalStateException("No se encontro vendedor con id: " + id));
						
	    List<Producto> productos = productoRepository.findByEmpresa_IdEmpresa(vendedor.getEmpresa().getIdEmpresa());
		
		return productos.stream().map(this::convertirDTO).toList();
	}
	
	@Override
	public List<ListarProductoDTO> listarProductos() {
	    List<Producto> productos = productoRepository.findAll();
		return productos.stream().map(this::convertirDTO).toList();
	}
	
	
	/**
	 * ============================
	 *       MÉTODOS PRIVADOS
	 * ============================
	 */
	

	private ListarProductoDTO convertirDTO(Producto producto) {
		
		ListarProductoDTO productoDTO = new ListarProductoDTO();
		
		productoDTO.setIdProducto(producto.getIdProducto());
		productoDTO.setNombreProducto(producto.getNombreProducto());
		productoDTO.setPrecio(producto.getPrecio());
		productoDTO.setStock(producto.getStock());
		productoDTO.setTalla(producto.getTalla());
		productoDTO.setColor(producto.getColor());
		productoDTO.setCategoria(producto.getCategoria());
		productoDTO.setImagenProducto(producto.getImagenProducto());
		
		return productoDTO;
	}
	
	private Producto obtenerProducto(Long id) {
		return productoRepository.findById(id)
				.orElseThrow(() -> new IllegalStateException("No se encontro producto con id: " + id));
	}

	
}


