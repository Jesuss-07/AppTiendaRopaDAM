package com.iesagora.jesus.apptienda.business.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iesagora.jesus.apptienda.business.model.DetallePedido;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long>{
	
	Optional<DetallePedido> findByIdPedidoAndIdProducto(Long idPedido, Long idProducto);

}
