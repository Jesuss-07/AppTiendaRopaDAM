package com.iesagora.jesus.apptienda.business.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "EMPRESA")
public class Empresa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_empresa")
	private Long idEmpresa;
	
	@Column(name = "nombre_empresa")
	private String nombreEmpresa;
	
	@Column(name = "cif", unique = true)
	private String cif;
	
	@Column(name = "email_contacto")
	private String emailContacto;
	
	@Column(name = "telefono_contacto")
	private String telefonoContacto;

	@Column(name = "direccion_sede")
	private String direccionSede;
	
	@Column(name = "logo_empresa")
	private String logoEmpresa;


}
