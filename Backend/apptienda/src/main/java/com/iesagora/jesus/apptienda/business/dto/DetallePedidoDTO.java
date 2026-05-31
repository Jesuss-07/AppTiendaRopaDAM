package com.iesagora.jesus.apptienda.business.dto;

import java.math.BigDecimal;

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
public class DetallePedidoDTO {
	
	private Long idDetallePedidoDTO;
	private ProductoCarritoDTO productoCarritoDTO;
	private Integer cantidad;
	private BigDecimal precioUnidad;
	private BigDecimal subtotal;

}
