package com.iesagora.jesus.apptienda.business.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iesagora.jesus.apptienda.business.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long>{

	boolean existsByCif(String cif);
	
}
