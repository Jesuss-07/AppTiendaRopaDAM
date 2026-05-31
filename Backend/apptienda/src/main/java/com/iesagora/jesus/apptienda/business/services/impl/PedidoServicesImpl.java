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
import com.iesagora.jesus.apptienda.business.repositories.ClienteRepository;
import com.iesagora.jesus.apptienda.business.repositories.DetallePedidoRepository;
import com.iesagora.jesus.apptienda.business.repositories.PedidoRepository;
import com.iesagora.jesus.apptienda.business.repositories.ProductoRepository;
import com.iesagora.jesus.apptienda.business.repositories.UsuarioRepository;
import com.iesagora.jesus.apptienda.business.services.PedidoServices;

import jakarta.transaction.Transactional;

@Service
public class PedidoServicesImpl implements PedidoServices{
	
	private final PedidoRepository pedidoRepository;
	private final DetallePedidoRepository detallePedidoRepository;
	private final UsuarioRepository usuarioRepository;
	private final ProductoRepository productoRepository;
	private final ClienteRepository clienteRepository;

	public PedidoServicesImpl (PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository, DetallePedidoRepository detallePedidoRepository, ProductoRepository productoRepository, ClienteRepository clienteRepository) {
		this.pedidoRepository = pedidoRepository;
		this.usuarioRepository = usuarioRepository;
		this.detallePedidoRepository = detallePedidoRepository;
		this.productoRepository = productoRepository;
		this.clienteRepository = clienteRepository;
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

	@Transactional
	@Override
	public void finalizarPedido(Long idUsuario) {
		Pedido pedido = pedidoRepository.findByIdUsuarioAndEstadoPedido(idUsuario, EstadoPedido.EN_PREPARACION)
				.orElseThrow(() -> new IllegalStateException("No se encuentra ningun pedido activo para este usuario"));
		
		procesarPago(pedido);
		System.out.println("Pagado");
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
	
	private void validarStock(Pedido pedido) {
		List<DetallePedido> detallePedido = detallePedidoRepository.findByIdPedido(pedido.getIdPedido());
		
		for(DetallePedido detalle : detallePedido) {
	        Producto producto = productoRepository.findById(detalle.getIdProducto())
	                .orElseThrow(() -> new IllegalStateException("Producto no existe"));
	        
	        if(producto.getStock() < detalle.getCantidad())
	        	throw new IllegalStateException("Stock insuficiente");
		}
	}
	
	private void validarPrecio(Pedido pedido) {
		
		Cliente cliente = datosCliente(pedido.getIdUsuario());
		
		BigDecimal saldo = cliente.getMonedero();
		
		if(saldo.compareTo(pedido.getTotal()) < 0 )
			throw new IllegalStateException("Saldo insuficiente");
		
	}
	
	private void descontarStock(Pedido pedido) {
		List<DetallePedido> detallePedido = detallePedidoRepository.findByIdPedido(pedido.getIdPedido());

		for(DetallePedido detalle : detallePedido) {
	        Producto producto = productoRepository.findById(detalle.getIdProducto())
	                .orElseThrow(() -> new IllegalStateException("Producto no existe"));

	        producto.setStock(producto.getStock() - detalle.getCantidad());
	        
	        productoRepository.save(producto);
		}
	}
	
	private void descontarMonedero(Pedido pedido) {
		Cliente cliente = datosCliente(pedido.getIdUsuario());

		BigDecimal saldoActual = cliente.getMonedero();
		BigDecimal total = pedido.getTotal();
		
		cliente.setMonedero(saldoActual.subtract(total));
		
		clienteRepository.save(cliente);
	}
	
	private void procesarPago(Pedido pedido) {
		validarStock(pedido);
		validarPrecio(pedido);
		descontarStock(pedido);
		descontarMonedero(pedido);
	}

}
