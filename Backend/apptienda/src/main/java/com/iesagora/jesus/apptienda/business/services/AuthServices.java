package com.iesagora.jesus.apptienda.business.services;

import com.iesagora.jesus.apptienda.business.dto.RegistroClienteDTO;
import com.iesagora.jesus.apptienda.business.dto.UsuarioDTO;
import com.iesagora.jesus.apptienda.business.model.Usuario;

public interface AuthServices {

	void registroCliente(RegistroClienteDTO registroClienteDTO);
	
	Usuario login(UsuarioDTO usuarioDTO);
	
}
