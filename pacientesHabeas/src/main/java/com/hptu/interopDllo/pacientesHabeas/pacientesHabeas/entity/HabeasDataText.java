package com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Table(name = "HABEAS_DATA_APP")
@Data
public class HabeasDataText {
    @Id
    @Column(name = "ID_HABEAS")
    private Long idHabeas;

    @Column(name = "TEXTO", columnDefinition = "TEXT")
    private String texto;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private LocalDateTime fechaModificacion;
}
