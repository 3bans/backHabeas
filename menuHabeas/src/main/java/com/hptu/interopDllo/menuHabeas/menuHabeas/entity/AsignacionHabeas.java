package com.hptu.interopDllo.menuHabeas.menuHabeas.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ASIGNACION_HABEAS")

public class AsignacionHabeas {

    @Id
    @Column(name = "ID_ASIGNACION")
    private Long idAsignacion;

    @ManyToOne
    @JoinColumn(name = "ID_MEDICO")
    private MaestroMedicos medico;

    @ManyToOne
    @JoinColumn(name = "ID_APLICACION")
    private Aplicaciones aplicacion;

    @ManyToOne
    @JoinColumn(name = "ID_HABEAS")
    private HabeasDataApp habeasData;

    @ManyToOne
    @JoinColumn(name = "ID_MOTIVO")
    private MaestroMotivosHabeas motivo;

    @Column(name = "FECHA_REGISTRO")
    private LocalDate fechaRegistro;

    // getters, setters
}
