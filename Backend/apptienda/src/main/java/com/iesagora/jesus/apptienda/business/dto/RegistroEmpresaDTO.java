package com.iesagora.jesus.apptienda.business.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class RegistroEmpresaDTO {

	private String nombreEmpresa;
	
	private String cif;
	
	@Email(message = "Formato de email erroneo")
	@NotBlank(message = "Email obligatorio")
	private String emailContacto;
	
	private String telefonoContacto;

	private String direccionSede;
	
	private String logoEmpresa;
	
}
