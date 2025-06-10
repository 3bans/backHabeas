package com.interopDllo.authServer.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import jakarta.persistence.Id;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ADMIN_USUARIO")
@Data
public class UserEntity {

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
    private Integer rolId;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "DEPARTAMENTO")
    private String departamento;

    @Column(name = "SECCION")
    private String seccion;
}