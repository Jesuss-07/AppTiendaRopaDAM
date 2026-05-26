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
public class ActualizarEmpresaDTO {
	
	private String nombreEmpresa;
	private String emailContacto;
	private String telefonoContacto;
	private String direccionSede;
	private String logoEmpresa;
	
}
