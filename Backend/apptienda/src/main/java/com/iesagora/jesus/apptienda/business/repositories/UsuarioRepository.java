package com.iesagora.jesus.apptienda.business.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iesagora.jesus.apptienda.business.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	boolean existsByEmail(String email);
	
	Usuario findByEmail(String email);
	
	List<Usuario> findByEstadoUsuarioTrue();
	
}
