package com.iesagora.jesus.apptienda.presentation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iesagora.jesus.apptienda.business.dto.CrearProductoDTO;
import com.iesagora.jesus.apptienda.business.model.Usuario;
import com.iesagora.jesus.apptienda.business.services.ProductoServices;

@RestController
@RequestMapping("/producto")
public class ProductoController {
	
	private ProductoServices productoServices;
	
	public ProductoController(ProductoServices productoServices) {
		this.productoServices = productoServices;
	}
	
	@PostMapping("/crear")
	@PreAuthorize("hasRole('VENDEDOR')")
	public ResponseEntity<?> crearProducto(@RequestBody CrearProductoDTO productoDTO){
		try {
			
			productoServices.crearProducto(productoDTO);
			
			return ResponseEntity.status(201).build();
		}catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
	
	@GetMapping("/listar")
	@PreAuthorize("hasRole('VENDEDOR')")
	public ResponseEntity<?> listarProductoVendedor(){
		try {
			return ResponseEntity.ok().body(productoServices.listarProductosPorVendedor(obtenerId()));
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
