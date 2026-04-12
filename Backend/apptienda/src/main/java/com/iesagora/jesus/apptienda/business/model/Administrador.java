package com.iesagora.jesus.apptienda.business.model;

import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Table(name = "Administrador")
@PrimaryKeyJoinColumn(name = "id_usuario")
public class Administrador extends Usuario{

}
