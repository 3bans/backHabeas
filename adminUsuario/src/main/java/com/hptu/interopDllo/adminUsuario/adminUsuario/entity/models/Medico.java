package com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MAESTRO_MEDICOS", schema = "dbo", catalog = "Sata")
@Getter
@Setter
public class Medico {

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