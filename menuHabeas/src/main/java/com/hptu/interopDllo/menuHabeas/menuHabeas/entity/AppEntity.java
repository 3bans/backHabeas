package com.hptu.interopDllo.menuHabeas.menuHabeas.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ADMIN_APP")
@Data
public class AppEntity {

    @Id
    @Column(name = "ID_APP")
    private Long idApp;

    @Column(name = "NOMBRE_APP")
    private String nombreApp;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "URL_APP")
    private String urlApp;

    @Column(name = "ESTADO")
    private String estado;
}