package com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ADMIN_USUARIO")
@Data
public class Usuarios {

    @Id
    @Column(name = "ID_USUARIO")
    private String idUsuario;

    @Column(name = "TIPOID_USUARIO")
    private String tipoIdUsuario;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "PUNTO_ATENCION")
    private String puntoAtencion;

    @Column(name = "ROL_ID")
    private String rolId;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "DEPARTAMENTO")
    private String departamento;

    @Column(name = "SECCION")
    private String seccion;
}

