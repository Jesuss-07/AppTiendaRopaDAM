package com.iesagora.jesus.apptienda.business.services;

import com.iesagora.jesus.apptienda.business.dto.RegistroClienteDTO;
import com.iesagora.jesus.apptienda.business.model.Cliente;

public interface AuthServices {

	void registroCliente(RegistroClienteDTO registroClienteDTO);
	
	Cliente loginCliente(String email, String password);
	
}
