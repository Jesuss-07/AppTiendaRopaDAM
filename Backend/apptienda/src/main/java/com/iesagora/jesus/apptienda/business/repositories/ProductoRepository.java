package com.iesagora.jesus.apptienda.business.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iesagora.jesus.apptienda.business.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
	
	List<Producto> findByEmpresa_IdEmpresa(Long idEmpresa);

}
