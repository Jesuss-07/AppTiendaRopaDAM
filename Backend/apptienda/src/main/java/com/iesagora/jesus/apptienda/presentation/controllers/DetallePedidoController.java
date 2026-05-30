package com.iesagora.jesus.apptienda.presentation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iesagora.jesus.apptienda.business.model.Usuario;
import com.iesagora.jesus.apptienda.business.services.DetallePedidoServices;

@RestController
@RequestMapping("/detalle")

public class DetallePedidoController {
	
	private DetallePedidoServices detallePedidoServices;
	
	public DetallePedidoController(DetallePedidoServices detallePedidoServices) {
		this.detallePedidoServices = detallePedidoServices;
	}
	
	@PostMapping("/anadir")
	@PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<?> anadirProducto(@RequestParam Long idProducto,@RequestParam int cantidad){
		try {
			detallePedidoServices.agregarProducto(obtenerId(), idProducto, cantidad);
			return ResponseEntity.ok().body("Se agrego el producto con exito");
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/eliminar")
	@PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<?> eliminarProducto(@RequestParam Long idDetallePedido){
		try {
			detallePedidoServices.eliminarProducto(idDetallePedido);
			return ResponseEntity.ok().body("Se elimino el producto con exito");
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/actualizar")
	@PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<?> actualizarDetallePedido(@RequestParam Long idDetallePedido,@RequestParam int cantidad){
		try {
			detallePedidoServices.actualizarCantidad(idDetallePedido, cantidad);
			return ResponseEntity.ok().body("Se actualizo Detalle Pedido");
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	/**
	 * ============================
	 *       MÉTODOS PRIVADOS
	 * ============================
	 */
	
	
	private Long obtenerId() {
 		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = (Usuario) auth.getPrincipal();		
		return usuario.getId();
	}

}
