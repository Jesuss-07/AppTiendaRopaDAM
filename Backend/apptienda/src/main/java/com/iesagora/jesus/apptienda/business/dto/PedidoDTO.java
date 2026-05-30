package com.iesagora.jesus.apptienda.business.dto;

import java.math.BigDecimal;
import java.util.List;

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
public class PedidoDTO {
	
	private List<DetallePedidoDTO> detallePedidoDTO;
	private BigDecimal total;
	private String direccionCliente;
	private String cpCliente;
	private String ciudadCliente;
	private String provinciaCliente;
	private String paisCliente;

}
