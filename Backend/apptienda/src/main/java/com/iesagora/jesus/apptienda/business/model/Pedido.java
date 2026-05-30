package com.iesagora.jesus.apptienda.business.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "PEDIDO")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pedido")
	private Long idPedido;
	
	@Column(name = "id_usuario")
	private Long idUsuario;
	
	@Column(name = "fecha_pedido")
	private LocalDateTime fechaPedido;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "estado_pedido")
	private EstadoPedido estadoPedido;
	
	@Column(name = "total")
	private BigDecimal total;
	
	@Column(name = "direccion_cliente")
	private String direccionCliente;
	
	@Column(name = "cp_cliente")
	private String cpCliente;
	
	@Column(name = "ciudad_cliente")
	private String ciudadCliente;
	
	@Column(name = "provincia_cliente")
	private String provinciaCliente;
	
	@Column(name = "pais_cliente")
	private String paisCliente;

}
