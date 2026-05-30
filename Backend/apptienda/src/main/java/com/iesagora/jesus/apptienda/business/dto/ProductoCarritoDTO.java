package com.iesagora.jesus.apptienda.business.dto;

import com.iesagora.jesus.apptienda.business.model.CategoriaRopa;
import com.iesagora.jesus.apptienda.business.model.Talla;

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
public class ProductoCarritoDTO {
	
    private String nombreProducto;
    private Talla talla;    
    private String color;
    private CategoriaRopa categoria;
    private String imagenProducto;

}
