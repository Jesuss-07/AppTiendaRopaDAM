package com.iesagora.jesus.apptienda.presentation.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iesagora.jesus.apptienda.business.dto.EditarClienteDTO;
import com.iesagora.jesus.apptienda.business.dto.EditarVendedorDTO;
import com.iesagora.jesus.apptienda.business.services.UsuarioServices;

@RestController
@RequestMapping("/user")
public class UsuarioController {
	
	private final UsuarioServices usuarioServices;
	
	public UsuarioController(UsuarioServices usuarioServices) {
		this.usuarioServices = usuarioServices;
	}
	
	@PutMapping("/bloquear")
	public ResponseEntity<?> bloquearUsuario(@RequestParam String email){
		try {
			return ResponseEntity.ok(Map.of("mensaje", usuarioServices.bloquearUsuario(email)));
		}catch(Exception e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/cliente/me")
	public ResponseEntity<?> getCliente(Authentication auth){
		try {
			String id = obtenerId(auth);	
			return ResponseEntity.ok(usuarioServices.obtenerCliente(id));
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}		
	}
	
	@PutMapping("/cliente/me")
	public ResponseEntity<?> setCliente(Authentication auth, @RequestBody EditarClienteDTO clienteDTO){
		try {
			String id = obtenerId(auth);	
			return ResponseEntity.ok(usuarioServices.actualizarCliente(id, clienteDTO));
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/vendedor/me")
	public ResponseEntity<?> getVendedor(Authentication auth){
		try {
			String id = obtenerId(auth);	
			return ResponseEntity.ok(usuarioServices.obtenerVendedor(id));
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}		
	}
	
	@PutMapping("/vendedor/me")
	public ResponseEntity<?> setVendedor(Authentication auth, @RequestBody EditarVendedorDTO vendedorDTO){
		try {
			String id = obtenerId(auth);	
			return ResponseEntity.ok(usuarioServices.actualizarVendedor(id, vendedorDTO));
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	/**
	 * ============================
	 *       MÉTODOS PRIVADOS
	 * ============================
	 */

	
	private static String obtenerId(Authentication auth) {
		return auth.getPrincipal().toString();	
	}
	
}
