package com.iesagora.jesus.apptienda.presentation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iesagora.jesus.apptienda.business.dto.RegistroClienteDTO;
import com.iesagora.jesus.apptienda.business.dto.RegistroVendedorDTO;
import com.iesagora.jesus.apptienda.business.dto.UsuarioDTO;
import com.iesagora.jesus.apptienda.business.services.AuthServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private AuthServices authServices;
	
	public AuthController(AuthServices authServices) {
		this.authServices = authServices;
	}
	
	@PostMapping("/registro/cliente")
	public ResponseEntity<?> registroCliente(@Valid @RequestBody RegistroClienteDTO clienteDTO) {
		
		try {
			authServices.registroCliente(clienteDTO);
			return ResponseEntity.status(201).build();
		} catch (Exception e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@PostMapping("/registro/vendedor")
	public ResponseEntity<?> registroVendedor(@Valid @RequestBody RegistroVendedorDTO vendedorDTO){
		
		try {
			authServices.registroVendedor(vendedorDTO);
			return ResponseEntity.status(201).build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody UsuarioDTO usuarioDTO) {
		
		try {
			return ResponseEntity.ok(authServices.login(usuarioDTO));
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	
}
