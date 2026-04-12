package com.iesagora.jesus.apptienda.business.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "Vendedor")
@PrimaryKeyJoinColumn(name = "id_usuario")
public class Vendedor extends Usuario{

}
