package com.iesagora.jesus.apptienda.business.services.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


import com.iesagora.jesus.apptienda.business.dto.RegistroClienteDTO;
import com.iesagora.jesus.apptienda.business.dto.RegistroVendedorDTO;
import com.iesagora.jesus.apptienda.business.dto.UsuarioDTO;
import com.iesagora.jesus.apptienda.business.model.Cliente;
import com.iesagora.jesus.apptienda.business.model.Empresa;
import com.iesagora.jesus.apptienda.business.model.Rol;
import com.iesagora.jesus.apptienda.business.model.Usuario;
import com.iesagora.jesus.apptienda.business.model.Vendedor;
import com.iesagora.jesus.apptienda.business.repositories.ClienteRepository;
import com.iesagora.jesus.apptienda.business.repositories.EmpresaRepository;
import com.iesagora.jesus.apptienda.business.repositories.UsuarioRepository;
import com.iesagora.jesus.apptienda.business.repositories.VendedorRepository;
import com.iesagora.jesus.apptienda.business.services.AuthServices;
import com.iesagora.jesus.apptienda.security.JwtUtils;

@Service
public class AuthServicesImpl implements AuthServices{
	
	private final UsuarioRepository 	usuarioRepository;
	private final ClienteRepository 	clienteRepository;
	private final VendedorRepository 	vendedorRepository;
	private final EmpresaRepository		empresaRepository;
	
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public AuthServicesImpl(UsuarioRepository usuarioRepository, ClienteRepository clienteRepository, VendedorRepository vendedorRepository, EmpresaRepository empresaRepository, PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.clienteRepository = clienteRepository;
		this.vendedorRepository = vendedorRepository;
		this.empresaRepository = empresaRepository;
		this.passwordEncoder = passwordEncoder;
	}


	@Override
	public void registroCliente(RegistroClienteDTO clienteDTO) {
		
		validarUsuarioNoExiste(clienteDTO.getEmail());
		
		Cliente cliente = crearCliente(clienteDTO);		
		clienteRepository.save(cliente);
	}
	
	@Override
	public void registroVendedor(RegistroVendedorDTO registroVendedorDTO) {
		
		validarUsuarioNoExiste(registroVendedorDTO.getEmail());
		
		Vendedor vendedor = crearVendedor(registroVendedorDTO);
		vendedorRepository.save(vendedor);
	}

	@Override
	public Map<String, String> login(UsuarioDTO usuarioDTO) {

		try {
		    Authentication authentication = authenticationManager.authenticate(
		        new UsernamePasswordAuthenticationToken(
		            usuarioDTO.getEmail(),
		            usuarioDTO.getPassword()
		        )
		    );
	
		    Usuario usuario = (Usuario) authentication.getPrincipal();
		    
		    if(!usuario.isEstadoUsuario())
		    	throw new IllegalStateException("Email Inhabilitado");
	
		    String token = jwtUtils.generarToken(usuario);
	
		    return Map.of("token", token);
		    
		}catch (BadCredentialsException e) {
			throw new IllegalStateException("Email o contraseña incorrectos");
		}
		    		    
	}
	
	
	/**
	 * ============================
	 *       MÉTODOS PRIVADOS
	 * ============================
	 */
	
	
	private void validarUsuarioNoExiste(String email) {
		if(usuarioRepository.existsByEmail(email))
			throw new IllegalStateException("Ya existe un usuario con este correo.");
	}
	
	private  Empresa obtenerEmpresaPorCif(String cif) {
		return empresaRepository.findByCif(cif)
				.orElseThrow(() -> new IllegalStateException("Empresa no encontrada"));
	}
	
	private Cliente crearCliente(RegistroClienteDTO clienteDTO) {
			
			Cliente cliente = new Cliente();
			
			cliente.setNombre(clienteDTO.getNombre());
			cliente.setApellido(clienteDTO.getApellido());
			cliente.setEmail(clienteDTO.getEmail());
			cliente.setPassword(passwordEncoder.encode(clienteDTO.getPassword()));
			cliente.setTelefono(clienteDTO.getTelefono());
			cliente.setEstadoUsuario(true);
			cliente.setRol(Rol.CLIENTE);
			cliente.setDireccion1(clienteDTO.getDireccion1());
			cliente.setDireccion2(clienteDTO.getDireccion2());
			cliente.setCp(clienteDTO.getCp());
			cliente.setPais(clienteDTO.getPais());
			cliente.setCiudad(clienteDTO.getCiudad());
			cliente.setProvincia(clienteDTO.getProvincia());
			cliente.setPuntos(0);
			cliente.setMonedero(BigDecimal.ZERO);
			
			return cliente;
	}
	
	private Vendedor crearVendedor(RegistroVendedorDTO vendedorDTO) {
		
		Vendedor vendedor = new Vendedor();
		
		vendedor.setEmpresa(obtenerEmpresaPorCif(vendedorDTO.getCif()));
		vendedor.setNombre(vendedorDTO.getNombre());
		vendedor.setApellido(vendedorDTO.getApellido());
		vendedor.setEmail(vendedorDTO.getEmail());
		vendedor.setPassword(passwordEncoder.encode(vendedorDTO.getPassword()));
		vendedor.setNumeroEmpleado(vendedorDTO.getNumeroEmpleado());
		vendedor.setEstadoUsuario(true);
		vendedor.setRol(Rol.VENDEDOR);
		
		return vendedor;
	}

}
