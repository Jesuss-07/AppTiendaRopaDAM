package com.iesagora.jesus.apptienda.business.services.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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
import com.iesagora.jesus.apptienda.business.services.DetallePedidoServices;

@Service
public class DetallePedidoServicesImpl implements DetallePedidoServices{
	
	private final DetallePedidoRepository detallePedidoRepository;
	private final PedidoRepository pedidoRepository;
	private final ProductoRepository productoRepository;
	private final UsuarioRepository usuarioRepository;
	
	public DetallePedidoServicesImpl(DetallePedidoRepository detallePedidoRepository, PedidoRepository pedidoRepository, ProductoRepository productoRepository, UsuarioRepository usuarioRepository) {
		this.detallePedidoRepository = detallePedidoRepository;
		this.pedidoRepository = pedidoRepository;
		this.productoRepository = productoRepository;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public void agregarProducto(Long idUsuario, Long idProducto, int cantidad) {

		Pedido pedido = pedidoRepository.findByIdUsuarioAndEstadoPedido(idUsuario, EstadoPedido.EN_PREPARACION)
				.orElseGet(() -> crearPedido(idUsuario));
		
		Optional<DetallePedido> detalleExistente = detallePedidoRepository.findByIdPedidoAndIdProducto(pedido.getIdPedido(), idProducto);
		
		Producto producto = productoRepository.findById(idProducto)
				.orElseThrow(() -> new IllegalStateException("No existe producto con id " + idProducto));
		
		if(detalleExistente.isPresent()) {
			DetallePedido detallePedido = detalleExistente.get();
			
			detallePedido.setCantidad(detallePedido.getCantidad() + cantidad);
	        detallePedido.setSubtotal(calcularSubtotal(detallePedido.getPrecioUnidad(), detallePedido.getCantidad()));

	        detallePedidoRepository.save(detallePedido);
	        
	        
		} else {
			
			DetallePedido detallePedido = new DetallePedido();
			
			detallePedido.setIdPedido(pedido.getIdPedido());
			detallePedido.setIdProducto(idProducto);
			detallePedido.setCantidad(cantidad);
			
			guardarPrecios(detallePedido, producto);
		}
		
		actualizarTotal(pedido.getIdPedido());
	}

	@Override
	public void eliminarProducto(Long idDetalleProducto) {
	    DetallePedido detalle = detallePedidoRepository.findById(idDetalleProducto)
	            .orElseThrow(() -> new IllegalStateException("No existe detalle"));

	    Long idPedido = detalle.getIdPedido();

	    detallePedidoRepository.delete(detalle);

	    actualizarTotal(idPedido);		
	}

	@Override
	public void actualizarCantidad(Long idDetalleProducto, int cantidad) {
		DetallePedido detallePedido = detallePedidoRepository.findById(idDetalleProducto)
				.orElseThrow(() -> new IllegalStateException("No se encontro el producto con id: " + idDetalleProducto));
		
		detallePedido.setCantidad(cantidad);
				
		detallePedido.setSubtotal(calcularSubtotal(detallePedido.getPrecioUnidad(), detallePedido.getCantidad()));
		
		detallePedidoRepository.save(detallePedido);
		
		actualizarTotal(detallePedido.getIdPedido());
	}
	
	
	/**
	 * ============================
	 *       MÉTODOS PRIVADOS
	 * ============================
	 */	

	private Pedido crearPedido(Long idUsuario) {
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
		return pedido;
	}
	
	private Cliente datosCliente(Long idUsuario) {
		Usuario usuario = usuarioRepository.findById(idUsuario)
				.orElseThrow(() -> new IllegalStateException("El usuario con id " + idUsuario + " no existe"));
		
		if (usuario instanceof Cliente cliente) {
			return cliente;
		} else {
		    throw new IllegalStateException("El usuario no es cliente");
		}
				
	}
	
	private void guardarPrecios(DetallePedido detallePedido, Producto producto) {
		detallePedido.setPrecioUnidad(producto.getPrecio());
		detallePedido.setSubtotal(calcularSubtotal(producto.getPrecio(), detallePedido.getCantidad()));
		detallePedidoRepository.save(detallePedido);
	}
	
	private BigDecimal calcularSubtotal (BigDecimal precio, int cantidad) {
		return precio.multiply(BigDecimal.valueOf(cantidad));
	}
	
	private void actualizarTotal(Long idPedido) {
	    List<DetallePedido> detallePedido =
	            detallePedidoRepository.findByIdPedido(idPedido);
	    
	    BigDecimal total = detallePedido.stream().map(DetallePedido::getSubtotal)
	    											.reduce(BigDecimal.ZERO, BigDecimal::add);
	    
	    Pedido pedido = pedidoRepository.findById(idPedido)
	            .orElseThrow(() -> new IllegalStateException("No existe el pedido"));	    
	    
	    pedido.setTotal(total);
	    
	    pedidoRepository.save(pedido);
	}
	
}
