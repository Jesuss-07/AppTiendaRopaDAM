package com.iesagora.jesus.apptienda.business.services;

import com.iesagora.jesus.apptienda.business.dto.RegistroClienteDTO;
import com.iesagora.jesus.apptienda.business.dto.RegistroVendedorDTO;
import com.iesagora.jesus.apptienda.business.dto.UsuarioDTO;

public interface AuthServices {

	void registroCliente(RegistroClienteDTO registroClienteDTO);
	
	void registroVendedor(RegistroVendedorDTO registroVendedorDTO);
	
	String login(UsuarioDTO usuarioDTO);
	
}
