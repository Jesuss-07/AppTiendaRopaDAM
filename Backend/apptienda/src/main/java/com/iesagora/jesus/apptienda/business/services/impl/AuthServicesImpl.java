package com.iesagora.jesus.apptienda.business.services.impl;

import java.math.BigDecimal;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iesagora.jesus.apptienda.business.dto.RegistroClienteDTO;
import com.iesagora.jesus.apptienda.business.model.Cliente;
import com.iesagora.jesus.apptienda.business.model.Rol;
import com.iesagora.jesus.apptienda.business.repository.ClienteRepository;
import com.iesagora.jesus.apptienda.business.repository.UsuarioRepository;
import com.iesagora.jesus.apptienda.business.services.AuthServices;

@Service
public class AuthServicesImpl implements AuthServices{
	
	private final UsuarioRepository usuarioRepository;
	private final ClienteRepository clienteRepository;
	
	private PasswordEncoder passwordEncoder;
	
	public AuthServicesImpl(UsuarioRepository usuarioRepository, ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.clienteRepository = clienteRepository;
		this.passwordEncoder = passwordEncoder;
	}


	@Override
	public void registroCliente(RegistroClienteDTO clienteDTO) {
		
		if(usuarioRepository.existsByEmail(clienteDTO.getEmail()))
			throw new IllegalStateException("Ya existe un usuario con este correo");
		
		Cliente cliente = crearCliente(clienteDTO);		
		clienteRepository.save(cliente);
	}
	
	private Cliente crearCliente(RegistroClienteDTO registroClienteDTO) {
		
		Cliente cliente = new Cliente();
		
		cliente.setNombre(registroClienteDTO.getNombre());
		cliente.setApellido(registroClienteDTO.getApellido());
		cliente.setEmail(registroClienteDTO.getEmail());
		cliente.setPassword(passwordEncoder.encode(registroClienteDTO.getPassword()));
		cliente.setTelefono(registroClienteDTO.getTelefono());
		cliente.setEstadoUsuario(true);
		cliente.setRol(Rol.CLIENTE);
		cliente.setDireccion1(registroClienteDTO.getDireccion1());
		cliente.setDireccion2(registroClienteDTO.getDireccion2());
		cliente.setCp(registroClienteDTO.getCp());
		cliente.setPais(registroClienteDTO.getPais());
		cliente.setCiudad(registroClienteDTO.getCiudad());
		cliente.setProvincia(registroClienteDTO.getProvincia());
		cliente.setPuntos(0);
		cliente.setMonedero(BigDecimal.ZERO);
		
		return cliente;
	}

}
