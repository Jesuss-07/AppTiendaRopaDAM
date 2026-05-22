package com.iesagora.jesus.apptienda.business.services.impl;

import org.springframework.stereotype.Service;

import com.iesagora.jesus.apptienda.business.model.Usuario;
import com.iesagora.jesus.apptienda.business.repositories.UsuarioRepository;
import com.iesagora.jesus.apptienda.business.services.UsuarioServices;

@Service
public class UsuarioServicesImpl implements UsuarioServices{
	
	private final UsuarioRepository usuarioRepository;
	
	public UsuarioServicesImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
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

}
