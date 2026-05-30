package com.iesagora.jesus.apptienda.presentation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iesagora.jesus.apptienda.business.model.Usuario;
import com.iesagora.jesus.apptienda.business.services.PedidoServices;

@RestController
@RequestMapping("/pedido")

public class PedidoController {
	
	private PedidoServices pedidoServices;
	
	public PedidoController(PedidoServices pedidoServices) {
		this.pedidoServices = pedidoServices;
	}
	
	
	@PostMapping("/pagar")
	@PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<?> pagarPedido(){
		try {
			pedidoServices.finalizarPedido(obtenerId());
			return ResponseEntity.ok().body("La compra fue realizada con exito");
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/listar")
	@PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<?> listarCarrito(){
		try {
			return ResponseEntity.ok().body(pedidoServices.mostrarPedido(obtenerId()));
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
