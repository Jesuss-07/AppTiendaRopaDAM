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
import com.iesagora.jesus.apptienda.business.model.Usuario;
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
		Long id = obtenerId(auth);
		try {
			return ResponseEntity.ok(usuarioServices.obtenerCliente(id));
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}		
	}
	
	@PutMapping("/cliente/me")
	public ResponseEntity<?> setCliente(Authentication auth, @RequestBody EditarClienteDTO clienteDTO){
		try {
			return ResponseEntity.ok(usuarioServices.actualizarCliente(obtenerId(auth), clienteDTO));
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/vendedor/me")
	public ResponseEntity<?> getVendedor(Authentication auth){
		try {
			return ResponseEntity.ok(usuarioServices.obtenerVendedor(obtenerId(auth)));
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}		
	}
	
	@PutMapping("/vendedor/me")
	public ResponseEntity<?> setVendedor(Authentication auth, @RequestBody EditarVendedorDTO vendedorDTO){
		try {
			return ResponseEntity.ok(usuarioServices.actualizarVendedor(obtenerId(auth), vendedorDTO));
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	/**
	 * ============================
	 *       MÉTODOS PRIVADOS
	 * ============================
	 */

	
	private static Long obtenerId(Authentication auth) {
		Usuario usuario = (Usuario) auth.getPrincipal();
		System.out.println(usuario.getId());
		return usuario.getId();
	}
	
}
