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
public class RegistroClienteDTO {
	
	private String 	nombre;
	private String 	apellido;
	
	@Email(message = "Formato de email erroneo")
	@NotBlank(message = "El email es obligatorio")
	private String 	email;
	
	@NotBlank(message = "La contraseña es obligatoria")
	private String 	password;
	private String 	telefono;
	private String 	direccion1;
	private String 	direccion2;
	private String 	cp;
	private String 	pais;
	private String 	ciudad;
	private String 	provincia;

}
