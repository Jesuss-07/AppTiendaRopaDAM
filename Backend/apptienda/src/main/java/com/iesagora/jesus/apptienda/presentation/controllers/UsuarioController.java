package com.iesagora.jesus.apptienda.presentation.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iesagora.jesus.apptienda.business.dto.EditarAdministradorDTO;
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
	
	@GetMapping("/cliente/me")
	@PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<?> getCliente(){
		System.out.println("Hola");
		try {
			
			Long id = obtenerId();
			
			System.out.println("Hola 2" + id);
			
			return ResponseEntity.ok(usuarioServices.obtenerCliente(id));
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}		
	}
	
	@GetMapping("/vendedor/me")
	@PreAuthorize("hasRole('VENDEDOR')")
	public ResponseEntity<?> getVendedor(){
		try {
						
			Long id = obtenerId();
			
			System.out.println("Actualizar user");
			
			return ResponseEntity.ok(usuarioServices.obtenerVendedor(id));
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}		
	}
	
	@GetMapping("/administrador/me")
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<?> getAdministrador(){
		try {
			
			Long id = obtenerId();

			return ResponseEntity.ok().body(usuarioServices.obtenerAdminDTO(id));
			
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/vendedores/empresa/{cif}")
	@PreAuthorize("hasAnyRole('ADMINISTRADOR','VENDEDOR')")
	public ResponseEntity<?> listaVendedoresEmpresa(@PathVariable String cif){
		try {
			return ResponseEntity.ok().body(usuarioServices.obtenerVendedoresCif(cif));
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/listar")
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<?> listarUsers(){
		try {
			return ResponseEntity.ok().body(usuarioServices.listarUsuarios());
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	
	@PutMapping("/bloquear")
	public ResponseEntity<?> bloquearUsuario(@RequestParam String email){
		try {
			return ResponseEntity.ok(Map.of("mensaje", usuarioServices.bloquearUsuario(email)));
		}catch(Exception e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/cliente/editar")
	@PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<?> setCliente(@RequestBody EditarClienteDTO clienteDTO){
		try {
			
			Long id = obtenerId(); 
			
			System.out.println("Actualizar user");
			
			return ResponseEntity.ok(usuarioServices.actualizarCliente(id, clienteDTO));
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/vendedor/editar")
	@PreAuthorize("hasRole('VENDEDOR')")
	public ResponseEntity<?> setVendedor(@RequestBody EditarVendedorDTO vendedorDTO){
		try {
			
			Long id = obtenerId(); 
					
			System.out.println("Actualizar user");
			
			return ResponseEntity.ok(usuarioServices.actualizarVendedor(id, vendedorDTO));
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/administrador/editar")
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	public ResponseEntity<?> setAdministrador(@RequestBody EditarAdministradorDTO administradorDTO){
		try {
			
			Long id = obtenerId(); 

			System.out.println("Actualizar user");
			return ResponseEntity.ok().body(usuarioServices.actualizarAdminDTO(id, administradorDTO));
			
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
