package com.iesagora.jesus.apptienda.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iesagora.jesus.apptienda.business.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	Cliente findByEmailAndPassword(String email, String password);
	
	Cliente findByEmail(String email);
	
	Boolean existsByEmail(String email);	
}
