package com.iesagora.jesus.apptienda.business.dto;

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
public class EmpresaDTO {
	
	private Long id;
	private String nombreEmpresa;
	private String direccionEmpresa;
	private String logoEmpresa;

}
