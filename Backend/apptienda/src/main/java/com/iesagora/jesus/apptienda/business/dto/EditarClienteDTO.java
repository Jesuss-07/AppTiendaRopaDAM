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
public class EditarClienteDTO {
	
	private String 	nombre;
	private String 	apellido;
	
	@Email(message = "Formato de email erroneo")
	private String 	email;
	
	private String 	password;
	private String 	telefono;
	private String 	direccion1;
	private String 	direccion2;
	private String 	cp;
	private String 	pais;
	private String 	ciudad;
	private String 	provincia;

}
