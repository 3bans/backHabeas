package com.hptu.interopDllo.menuHabeas.menuHabeas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "MAESTRO_MOTIVOS_HABEAS")
@Getter
@Setter
public class MaestroMotivosHabeas {
     @Id
    @Column(name = "ID_MOTIVO")
    private Long idMotivo;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "ESTADO")
    private String estado;
}
