package com.hptu.interopDllo.menuHabeas.menuHabeas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MAESTRO_MEDICOS")
@Getter
@Setter
public class MaestroMedicos {

    @Id
    @Column(name = "ID_MEDICO")
    private Long idMedico;

    @Column(name = "IDENTIFICACION")
    private String identificacion;

    @Column(name = "NOMBRE_COMPLETO")
    private String nombreCompleto;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "ESPECIALIDAD")
    private String especialidad;

    @Column(name = "CONSULTORIO")
    private String consultorio;
}
