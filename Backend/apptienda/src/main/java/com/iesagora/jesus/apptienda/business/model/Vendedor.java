package com.iesagora.jesus.apptienda.business.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "Vendedor")
@PrimaryKeyJoinColumn(name = "id_usuario")
public class Vendedor extends Usuario{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "id_empresa")
	private Empresa empresa;
	
	@Column(name = "numero_empleado")
	private String numeroEmpleado;
	
}
