package com.iesagora.jesus.apptienda.presentation.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	public ResponseEntity<?> getCliente(){
		System.out.println("Hola");
		try {
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			Usuario usuario = (Usuario) auth.getPrincipal();
			
			Long id = usuario.getId(); 
			
			System.out.println("Hola 2" + id);
			
			return ResponseEntity.ok(usuarioServices.obtenerCliente(id));
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}		
	}
	
	@PutMapping("/cliente/editar")
	public ResponseEntity<?> setCliente(@RequestBody EditarClienteDTO clienteDTO){
		try {
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			Usuario usuario = (Usuario) auth.getPrincipal();
			
			Long id = usuario.getId(); 
			
			System.out.println("Actualizar user");
			
			return ResponseEntity.ok(usuarioServices.actualizarCliente(id, clienteDTO));
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	/*
	
	@GetMapping("/vendedor/me")
	public ResponseEntity<?> getVendedor(Authentication auth){
		try {
			return ResponseEntity.ok(usuarioServices.obtenerVendedor());
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}		
	}
	
	@PutMapping("/vendedor/me")
	public ResponseEntity<?> setVendedor(@RequestBody EditarVendedorDTO vendedorDTO){
		try {
			return ResponseEntity.ok(usuarioServices.actualizarVendedor(, vendedorDTO));
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}*/
	
}
