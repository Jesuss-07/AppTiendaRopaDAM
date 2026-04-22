package com.iesagora.jesus.apptienda.business.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iesagora.jesus.apptienda.business.model.Vendedor;

public interface VendedorRepository extends JpaRepository<Vendedor, Long>{

	Vendedor findByEmail(String email);
		
}
