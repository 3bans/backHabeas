package com.hptu.interopDllo.menuHabeas.menuHabeas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "APLICACIONES")
@Getter
@Setter
public class Aplicaciones {

    @Id
    @Column(name = "ID_APLICACION")
    private Long idAplicacion;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ESTADO")
    private String estado;
}
