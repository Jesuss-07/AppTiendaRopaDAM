package com.iesagora.jesus.apptienda.business.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "DETALLE_PEDIDO")
@PrimaryKeyJoinColumn(name = "id_detalle")
public class DetallePedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_detalle")
	private Long idDetalle;
	
	@Column(name = "id_pedido")
	private Long idPedido;
	
	@Column(name = "id_producto")
	private Long idProducto;
	
	@Column(name = "cantidad")
	private Integer cantidad;
	
	@Column(name = "precio_unidad")
	private BigDecimal precioUnidad;
	
	@Column(name = "subtotal")
	private BigDecimal subtotal;
	
	

}
