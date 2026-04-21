package com.iesagora.jesus.apptienda.business.services;

import com.iesagora.jesus.apptienda.business.dto.RegistroClienteDTO;
import com.iesagora.jesus.apptienda.business.dto.UsuarioDTO;

public interface AuthServices {

	void registroCliente(RegistroClienteDTO registroClienteDTO);
	
	String login(UsuarioDTO usuarioDTO);
	
}
