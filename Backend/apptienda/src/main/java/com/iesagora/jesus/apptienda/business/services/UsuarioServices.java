package com.iesagora.jesus.apptienda.business.services;

import java.util.List;

import com.iesagora.jesus.apptienda.business.dto.EditarClienteDTO;
import com.iesagora.jesus.apptienda.business.dto.EditarVendedorDTO;
import com.iesagora.jesus.apptienda.business.dto.VistaVendedorDTO;
import com.iesagora.jesus.apptienda.business.dto.AdministradorDTO;

public interface UsuarioServices {
	
	String bloquearUsuario(String email);
	
	EditarClienteDTO obtenerCliente(Long id);
	
	EditarVendedorDTO obtenerVendedor(Long id);
	
	EditarClienteDTO actualizarCliente(Long id, EditarClienteDTO editarClienteDTO);
	
	EditarVendedorDTO actualizarVendedor(Long id, EditarVendedorDTO editarVendedorDTO);
	
	List<VistaVendedorDTO> obtenerVendedoresCif(String cif);
	
	AdministradorDTO obtenerAdminDTO(Long id);

}
