package com.iesagora.jesus.apptienda.business.services;

import java.util.List;

import com.iesagora.jesus.apptienda.business.dto.EmpresaDTO;
import com.iesagora.jesus.apptienda.business.dto.RegistroEmpresaDTO;

public interface EmpresaServices {

	void registroEmpresa(RegistroEmpresaDTO empresaDTO);
	
	List<EmpresaDTO> obtenerEmpresas();
	
}
