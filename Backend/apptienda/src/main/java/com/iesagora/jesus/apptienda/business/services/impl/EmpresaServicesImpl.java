package com.iesagora.jesus.apptienda.business.services.impl;

import org.springframework.stereotype.Service;

import com.iesagora.jesus.apptienda.business.dto.RegistroEmpresaDTO;
import com.iesagora.jesus.apptienda.business.model.Empresa;
import com.iesagora.jesus.apptienda.business.repositories.EmpresaRepository;
import com.iesagora.jesus.apptienda.business.services.EmpresaServices;

@Service
public class EmpresaServicesImpl implements EmpresaServices{
	
	private final EmpresaRepository empresaRepository;
	
	public EmpresaServicesImpl(EmpresaRepository empresaRepository) {
		this.empresaRepository = empresaRepository;
	}

	@Override
	public void registroEmpresa(RegistroEmpresaDTO empresaDTO) {

		if(empresaRepository.existsByCif(empresaDTO.getCif()))
			throw new IllegalStateException("Ya existe una empresa con este CIF");
		
		Empresa empresa = crearEmpresa(empresaDTO);
		empresaRepository.save(empresa);
		
	}
	
	
	/**
	 * ============================
	 *       MÉTODOS PRIVADOS
	 * ============================
	 */
	
	
	private Empresa crearEmpresa(RegistroEmpresaDTO empresaDTO) {
		
		Empresa empresa = new Empresa();
		
		empresa.setCif(empresaDTO.getCif());
		empresa.setDireccionSede(empresaDTO.getDireccionSede());
		empresa.setEmailContacto(empresaDTO.getEmailContacto());
		empresa.setLogoEmpresa(empresaDTO.getLogoEmpresa());
		empresa.setNombreEmpresa(empresaDTO.getNombreEmpresa());
		empresa.setTelefonoContacto(empresaDTO.getTelefonoContacto());
		
		return empresa;
	}

}
