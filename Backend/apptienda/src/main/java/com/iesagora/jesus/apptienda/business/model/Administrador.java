package com.iesagora.jesus.apptienda.business.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "ADMINISTRADOR")
@PrimaryKeyJoinColumn(name = "id_usuario")
public class Administrador extends Usuario{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
