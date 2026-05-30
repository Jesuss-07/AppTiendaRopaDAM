package com.iesagora.jesus.apptienda.business.services.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.iesagora.jesus.apptienda.business.dto.DetallePedidoDTO;
import com.iesagora.jesus.apptienda.business.dto.PedidoDTO;
import com.iesagora.jesus.apptienda.business.dto.ProductoCarritoDTO;
import com.iesagora.jesus.apptienda.business.model.Cliente;
import com.iesagora.jesus.apptienda.business.model.DetallePedido;
import com.iesagora.jesus.apptienda.business.model.EstadoPedido;
import com.iesagora.jesus.apptienda.business.model.Pedido;
import com.iesagora.jesus.apptienda.business.model.Producto;
import com.iesagora.jesus.apptienda.business.model.Usuario;
import com.iesagora.jesus.apptienda.business.repositories.DetallePedidoRepository;
import com.iesagora.jesus.apptienda.business.repositories.PedidoRepository;
import com.iesagora.jesus.apptienda.business.repositories.ProductoRepository;
import com.iesagora.jesus.apptienda.business.repositories.UsuarioRepository;
import com.iesagora.jesus.apptienda.business.services.PedidoServices;

@Service
public class PedidoServicesImpl implements PedidoServices{
	
	private final PedidoRepository pedidoRepository;
	private final DetallePedidoRepository detallePedidoRepository;
	private final UsuarioRepository usuarioRepository;
	private final ProductoRepository productoRepository;

	public PedidoServicesImpl (PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository, DetallePedidoRepository detallePedidoRepository, ProductoRepository productoRepository) {
		this.pedidoRepository = pedidoRepository;
		this.usuarioRepository = usuarioRepository;
		this.detallePedidoRepository = detallePedidoRepository;
		this.productoRepository = productoRepository;
	}

	@Override
	public void crearPedido(Long idUsuario) {
		Pedido pedido = new Pedido();
		
		pedido.setIdUsuario(idUsuario);
		pedido.setEstadoPedido(EstadoPedido.EN_PREPARACION);
		pedido.setTotal(BigDecimal.ZERO);
		pedido.setFechaPedido(LocalDateTime.now());
		
		Cliente cliente = datosCliente(idUsuario);
		
		pedido.setCiudadCliente(cliente.getCiudad());
		pedido.setCpCliente(cliente.getCp());
		pedido.setDireccionCliente(cliente.getDireccion1());
		pedido.setPaisCliente(cliente.getPais());
		pedido.setProvinciaCliente(cliente.getProvincia());
		
		pedidoRepository.save(pedido);
	}

	@Override
	public PedidoDTO mostrarPedido(Long idUsuario) {
		Pedido pedido = pedidoRepository.findByIdUsuarioAndEstadoPedido(idUsuario, EstadoPedido.EN_PREPARACION)
				.orElseThrow(() -> new IllegalStateException("No se encuentra ningun pedido activo para este usuario"));
		
		return mapearPedidoDTO(pedido);
	}

	@Override
	public void finalizarPedido(Long idUsuario) {
		Pedido pedido = pedidoRepository.findByIdUsuarioAndEstadoPedido(idUsuario, EstadoPedido.EN_PREPARACION)
				.orElseThrow(() -> new IllegalStateException("No se encuentra ningun pedido activo para este usuario"));
		
		pedido.setEstadoPedido(EstadoPedido.PAGADO);
		pedido.setFechaPedido(LocalDateTime.now());
		
		pedidoRepository.save(pedido);
	}
	
	
	/**
	 * ============================
	 *       MÉTODOS PRIVADOS
	 * ============================
	 */
	
	
	private Cliente datosCliente(Long idUsuario) {
		Usuario usuario = usuarioRepository.findById(idUsuario)
				.orElseThrow(() -> new IllegalStateException("El usuario con id " + idUsuario + " no existe"));
		
		if (usuario instanceof Cliente cliente) {
			return cliente;
		} else {
		    throw new IllegalStateException("El usuario no es cliente");
		}
				
	}
	
	private PedidoDTO mapearPedidoDTO(Pedido pedido) {
	    PedidoDTO pedidoDTO = new PedidoDTO();

	    pedidoDTO.setTotal(pedido.getTotal());
	    pedidoDTO.setDireccionCliente(pedido.getDireccionCliente());
	    pedidoDTO.setCpCliente(pedido.getCpCliente());
	    pedidoDTO.setCiudadCliente(pedido.getCiudadCliente());
	    pedidoDTO.setProvinciaCliente(pedido.getProvinciaCliente());
	    pedidoDTO.setPaisCliente(pedido.getPaisCliente());
	    
	    List<DetallePedido> detallePedido = detallePedidoRepository.findByIdPedido(pedido.getIdPedido());
	    
	    List<DetallePedidoDTO> detallePedidoDTO = detallePedido.stream().map(this::mapearDetallePedidoDTO).toList();
	    
	    pedidoDTO.setDetallePedidoDTO(detallePedidoDTO);
		
	    return pedidoDTO;
	}
	
	private DetallePedidoDTO mapearDetallePedidoDTO(DetallePedido detallePedido) {
	    DetallePedidoDTO detallePedidoDTO = new DetallePedidoDTO();

	    detallePedidoDTO.setCantidad(detallePedido.getCantidad());
	    detallePedidoDTO.setPrecioUnidad(detallePedido.getPrecioUnidad());
	    detallePedidoDTO.setSubtotal(detallePedido.getSubtotal());
	    detallePedidoDTO.setProductoCarritoDTO(mapearProductoDTO(detallePedido.getIdProducto()));	    
		
	    return detallePedidoDTO;
	}
	
	private ProductoCarritoDTO mapearProductoDTO(Long idProducto) {
		ProductoCarritoDTO carritoDTO = new ProductoCarritoDTO();
		Producto producto = productoRepository.findById(idProducto)
				.orElseThrow(() -> new IllegalStateException("No existe producto con este id"));
		
		carritoDTO.setCategoria(producto.getCategoria());
		carritoDTO.setColor(producto.getColor());
		carritoDTO.setImagenProducto(producto.getImagenProducto());
		carritoDTO.setNombreProducto(producto.getNombreProducto());
		carritoDTO.setTalla(producto.getTalla());
		
		return carritoDTO;
	}


}
