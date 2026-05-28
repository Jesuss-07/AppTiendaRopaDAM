package com.iesagora.jesus.apptienda.presentation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iesagora.jesus.apptienda.business.dto.CrearProductoDTO;
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

}
