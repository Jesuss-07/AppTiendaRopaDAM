package com.iesagora.jesus.apptienda.business.services;

import com.iesagora.jesus.apptienda.business.dto.EditarClienteDTO;
import com.iesagora.jesus.apptienda.business.dto.EditarVendedorDTO;

public interface UsuarioServices {
	
	String bloquearUsuario(String email);
	
	EditarClienteDTO obtenerCliente(Long id);
	
	EditarVendedorDTO obtenerVendedor(Long id);
	
	EditarClienteDTO actualizarCliente(Long id, EditarClienteDTO editarClienteDTO);
	
	EditarVendedorDTO actualizarVendedor(Long id, EditarVendedorDTO editarVendedorDTO);

}
