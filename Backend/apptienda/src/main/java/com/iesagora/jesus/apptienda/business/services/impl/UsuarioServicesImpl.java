package com.iesagora.jesus.apptienda.business.services.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iesagora.jesus.apptienda.business.dto.EditarClienteDTO;
import com.iesagora.jesus.apptienda.business.dto.EditarVendedorDTO;
import com.iesagora.jesus.apptienda.business.dto.VistaVendedorDTO;
import com.iesagora.jesus.apptienda.business.model.Cliente;
import com.iesagora.jesus.apptienda.business.model.Usuario;
import com.iesagora.jesus.apptienda.business.model.Vendedor;
import com.iesagora.jesus.apptienda.business.repositories.UsuarioRepository;
import com.iesagora.jesus.apptienda.business.repositories.VendedorRepository;
import com.iesagora.jesus.apptienda.business.services.UsuarioServices;

@Service
public class UsuarioServicesImpl implements UsuarioServices{
	
	private final UsuarioRepository 	usuarioRepository;
	private final VendedorRepository 	vendedorRepository;
	private final PasswordEncoder 		passwordEncoder;
	
	public UsuarioServicesImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, VendedorRepository vendedorRepository) {
		this.usuarioRepository 	= usuarioRepository;
		this.passwordEncoder 	= passwordEncoder;
		this.vendedorRepository = vendedorRepository;
	}

	@Override
	public String bloquearUsuario(String email) {

		Usuario usuario = usuarioRepository.findByEmail(email);
		
		if (usuario == null) 
			throw new IllegalStateException("Usuario no encontrado");
		
		usuario.setEstadoUsuario(false);
		usuarioRepository.save(usuario);
		
		return "Email bloqueado demasiados intentos";
	}

	@Override
	public EditarClienteDTO obtenerCliente(Long id) {
		
		Usuario usuario = obtenerUsuarioId(id);
		
		if (!(usuario instanceof Cliente cliente)) 
	        throw new IllegalStateException("El usuario no es cliente");
	    
		
		return cargarClienteDTO(cliente);
	}

	@Override
	public EditarVendedorDTO obtenerVendedor(Long id) {
		
		Usuario usuario = obtenerUsuarioId(id);
		
		if(!(usuario instanceof Vendedor vendedor)) 
			throw new IllegalStateException("El usuario no es un vendedor");
		
		
		return cargarVendedorDTO(vendedor);
	}

	@Override
	public EditarClienteDTO actualizarCliente(Long id, EditarClienteDTO clienteDTO) {

		Usuario usuario = obtenerUsuarioId(id);
		
		if(!(usuario instanceof Cliente cliente)) 
			throw new IllegalStateException("El usuario no es un cliente");
		
		cliente.setNombre(clienteDTO.getNombre());
	    cliente.setApellido(clienteDTO.getApellido());
	    
	    validarEmail(clienteDTO.getEmail(), id);
		cliente.setEmail(clienteDTO.getEmail());

	    cliente.setTelefono(clienteDTO.getTelefono());
	    cliente.setDireccion1(clienteDTO.getDireccion1());
	    cliente.setDireccion2(clienteDTO.getDireccion2());
	    cliente.setCp(clienteDTO.getCp());
	    cliente.setPais(clienteDTO.getPais());
	    cliente.setCiudad(clienteDTO.getCiudad());
	    cliente.setProvincia(clienteDTO.getProvincia());
	    actualizarPassword(cliente, clienteDTO.getPassword());
		
	    usuarioRepository.save(cliente);
	    
		return cargarClienteDTO(cliente);
	}

	@Override
	public EditarVendedorDTO actualizarVendedor(Long id, EditarVendedorDTO vendedorDTO) {

		Usuario usuario = obtenerUsuarioId(id);
		
		if(!(usuario instanceof Vendedor vendedor)) 
			throw new IllegalStateException("El usuario no es un vendedor");

		vendedor.setNombre(vendedorDTO.getNombre());
		vendedor.setApellido(vendedorDTO.getApellido());
		validarEmail(vendedorDTO.getEmail(), id);
		vendedor.setEmail(vendedorDTO.getEmail());
		
		actualizarPassword(vendedor, vendedorDTO.getPassword());
		
		usuarioRepository.save(vendedor);
		
		return cargarVendedorDTO(vendedor);
	}
	
	@Override
	public List<VistaVendedorDTO> obtenerVendedoresCif(String cif) {
		
		List<Vendedor> vendedores = vendedorRepository.findByEmpresaCif(cif);
		
		return vendedores.stream().map(vendedor -> {
			VistaVendedorDTO vendedorDTO = new VistaVendedorDTO();
			
			vendedorDTO.setNombre(vendedor.getNombre());
			vendedorDTO.setApellido(vendedor.getApellido());
			vendedorDTO.setEmailCorporativo(vendedor.getEmail());
			
			return vendedorDTO;
		}).toList();
	}
	
	
	/**
	 * ============================
	 *       MÉTODOS PRIVADOS
	 * ============================
	 */
	
	
	private EditarClienteDTO cargarClienteDTO(Cliente usuario) {
		EditarClienteDTO clienteDTO = new EditarClienteDTO();
		
		clienteDTO.setNombre(usuario.getNombre());
		clienteDTO.setApellido(usuario.getApellido());
		clienteDTO.setEmail(usuario.getEmail());
		clienteDTO.setTelefono(usuario.getTelefono());
		clienteDTO.setDireccion1(usuario.getDireccion1());
		clienteDTO.setDireccion2(usuario.getDireccion2());
		clienteDTO.setCp(usuario.getCp());
		clienteDTO.setPais(usuario.getPais());
		clienteDTO.setCiudad(usuario.getCiudad());
		clienteDTO.setProvincia(usuario.getProvincia());
		System.out.println("Usuario" + usuario.toString() + " " + clienteDTO.toString());
		
		return clienteDTO;
	}
	
	private EditarVendedorDTO cargarVendedorDTO(Vendedor usuario) {
		EditarVendedorDTO vendedorDTO = new EditarVendedorDTO();
		
		vendedorDTO.setNombre(usuario.getNombre());
		vendedorDTO.setApellido(usuario.getApellido());
		vendedorDTO.setEmail(usuario.getEmail());
		
		return vendedorDTO;
	}

	private Usuario obtenerUsuarioId(Long id) {
		return usuarioRepository.findById(id)
		        .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));
	}
	
	private void actualizarPassword(Usuario usuario, String password) {
		if(password != null && !password.isBlank()) {
			usuario.setPassword(passwordEncoder.encode(password));
		}
	}
	
	private void validarEmail(String email, Long id) {
		Usuario usuario = usuarioRepository.findByEmail(email);
		
		if(usuario != null && !usuario.getId().equals(id))
			throw new IllegalStateException("El email ya pertenece a otra persona");
		
	}
	
}
