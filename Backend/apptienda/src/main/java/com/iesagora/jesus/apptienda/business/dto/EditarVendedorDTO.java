package com.iesagora.jesus.apptienda.business.dto;

import jakarta.validation.constraints.Email;
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
public class EditarVendedorDTO {
	
	private String 	nombre;
	private String 	apellido;
	
	@Email(message = "Formato de email erroneo")
	private String 	email;
	
	private String 	password;
	

}
