package com.iesagora.jesus.apptienda.business.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.iesagora.jesus.apptienda.business.dto.EmpresaDTO;
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
	
	@Override
	public List<EmpresaDTO> obtenerEmpresas() {
		
		List<Empresa> empresas = empresaRepository.findAll();
		
		return empresas.stream()
				.map(empresa -> new EmpresaDTO(
						empresa.getIdEmpresa(),
						empresa.getNombreEmpresa(),
						empresa.getDireccionSede(),
						empresa.getLogoEmpresa())).toList();
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
