package com.iesagora.jesus.apptienda.business.services;

import java.util.List;

import com.iesagora.jesus.apptienda.business.dto.ActualizarEmpresaDTO;
import com.iesagora.jesus.apptienda.business.dto.EmpresaDTO;
import com.iesagora.jesus.apptienda.business.dto.RegistroEmpresaDTO;
import com.iesagora.jesus.apptienda.business.dto.VistaEmpresaDTO;

public interface EmpresaServices {

	void registroEmpresa(RegistroEmpresaDTO empresaDTO);
	
	List<EmpresaDTO> obtenerEmpresas();
	
	ActualizarEmpresaDTO obtenerEmpresaId(Long id);
	
	void actualizarEmpresa(Long id, ActualizarEmpresaDTO empresaDTO);
	
	void borrarEmpresa(Long id);
	
	VistaEmpresaDTO obtenerVistaEmpresa(Long id);
	
	Long obtenerIdEmpresaVendedor(Long id);
	
}
