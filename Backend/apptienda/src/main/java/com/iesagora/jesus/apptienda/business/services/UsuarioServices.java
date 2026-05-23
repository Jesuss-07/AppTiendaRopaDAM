package com.iesagora.jesus.apptienda.business.services;

import com.iesagora.jesus.apptienda.business.dto.EditarClienteDTO;
import com.iesagora.jesus.apptienda.business.dto.EditarVendedorDTO;

public interface UsuarioServices {
	
	String bloquearUsuario(String email);
	
	EditarClienteDTO obtenerCliente(String id);
	
	EditarVendedorDTO obtenerVendedor(String id);
	
	EditarClienteDTO actualizarCliente(String id, EditarClienteDTO editarClienteDTO);
	
	EditarVendedorDTO actualizarVendedor(String id, EditarVendedorDTO editarVendedorDTO);

}
