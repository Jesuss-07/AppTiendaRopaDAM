package com.iesagora.jesus.apptienda.presentation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iesagora.jesus.apptienda.business.dto.RegistroEmpresaDTO;
import com.iesagora.jesus.apptienda.business.services.EmpresaServices;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {
	
	private EmpresaServices empresaServices;
	
	public EmpresaController(EmpresaServices empresaServices) {
		this.empresaServices = empresaServices;
	}
	
	@PostMapping("/registro")
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<?> registroEmpresa(@RequestBody RegistroEmpresaDTO empresaDTO){
		
		try {
			empresaServices.registroEmpresa(empresaDTO);
			return ResponseEntity.status(201).build();
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@GetMapping("/lista")
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<?> listarEmpresas(){
		try {
			return ResponseEntity.ok(empresaServices.obtenerEmpresas());
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
