package com.iesagora.jesus.apptienda.presentation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iesagora.jesus.apptienda.business.dto.RegistroClienteDTO;
import com.iesagora.jesus.apptienda.business.services.AuthServices;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private AuthServices authServices;
	
	public AuthController(AuthServices authServices) {
		this.authServices = authServices;
	}
	
	@PostMapping("/registroCliente")
	public ResponseEntity<?> registroCliente(@RequestBody RegistroClienteDTO clienteDTO) {

		try {
			authServices.registroCliente(clienteDTO);
			return ResponseEntity.status(201).build();
		} catch (Exception e){
			return ResponseEntity.badRequest().body("ERROR EN EL REGISTRO");
		}
		
	}
	
	
}
