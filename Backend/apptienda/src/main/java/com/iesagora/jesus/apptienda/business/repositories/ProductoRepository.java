package com.iesagora.jesus.apptienda.business.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iesagora.jesus.apptienda.business.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{

}
