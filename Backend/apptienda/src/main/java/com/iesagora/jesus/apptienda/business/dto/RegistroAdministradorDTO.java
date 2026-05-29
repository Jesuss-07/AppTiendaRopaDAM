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
public class RegistroAdministradorDTO {

	private String 	nombre;
	private String 	apellido;
	private String 	email;
	private String 	password;
	private String 	telefono;

}
