package com.iesagora.jesus.apptienda.business.dto;

import com.iesagora.jesus.apptienda.business.model.Rol;

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
public class ListarUsuarioDTO {
	
	private Long 	id;
	private String 	nombre;
	private String 	apellido;
	private String 	email;
	private Rol 	rol;

}
