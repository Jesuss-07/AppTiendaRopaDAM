package com.iesagora.jesus.apptienda.business.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
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
@Table(name = "Cliente")
@PrimaryKeyJoinColumn(name = "id_usuario")
public class Cliente extends Usuario {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String 		direccion1;
	private String 		direccion2;
	private String 		cp;
	private String 		pais;
	private String 		ciudad;
	private String 		provincia;
	private int			puntos;
	private BigDecimal 	monedero;

}
