package com.iesagora.jesus.apptienda.business.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iesagora.jesus.apptienda.business.model.EstadoPedido;
import com.iesagora.jesus.apptienda.business.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{

	Optional<Pedido> findByIdUsuarioAndEstadoPedido(Long idUsuario, EstadoPedido estadoPedido);
	
}
