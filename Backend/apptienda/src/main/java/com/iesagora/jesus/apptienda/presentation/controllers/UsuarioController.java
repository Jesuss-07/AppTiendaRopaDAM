package com.iesagora.jesus.apptienda.presentation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iesagora.jesus.apptienda.business.services.UsuarioServices;

@RestController
@RequestMapping("/opciones")
public class UsuarioController {
	
	private UsuarioServices usuarioServices;
	
	public UsuarioController(UsuarioServices usuarioServices) {
		this.usuarioServices = usuarioServices;
	}
	
	@PutMapping("/bloquear")
	public ResponseEntity<?> bloquearUsuario(@RequestParam String email){
		try {
			return ResponseEntity.ok(usuarioServices.bloquearUsuario(email));
		}catch(Exception e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
