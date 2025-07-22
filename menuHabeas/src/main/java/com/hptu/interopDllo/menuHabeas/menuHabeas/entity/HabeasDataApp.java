package com.hptu.interopDllo.menuHabeas.menuHabeas.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "HABEAS_DATA_APP")
@Getter
@Setter
public class HabeasDataApp {

    @Id
    @Column(name = "ID_HABEAS")
    private Long idHabeas;

    @Column(name = "TEXTO")
    private String texto;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "FECHA_CREACION")
    private LocalDate fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    private LocalDate fechaModificacion;
}
