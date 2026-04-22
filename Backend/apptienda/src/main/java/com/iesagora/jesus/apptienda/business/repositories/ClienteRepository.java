package com.iesagora.jesus.apptienda.business.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iesagora.jesus.apptienda.business.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	Cliente findByEmail(String email);
	
}
