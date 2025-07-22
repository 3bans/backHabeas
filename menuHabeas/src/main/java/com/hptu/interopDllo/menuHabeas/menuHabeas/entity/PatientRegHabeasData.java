package com.hptu.interopDllo.menuHabeas.menuHabeas.entity;

import java.time.LocalDate;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "PATIENT_REG_HABEAS_DATA")
@Data
public class PatientRegHabeasData {

    @Id
    @Column(name = "ID_REGISTRO")
    private Long idRegistro;

    @Column(name = "NO_IDENTIFICACION")
    private String noIdentificacion;

    @Column(name = "TIPO_ID")
    private String tipoId;

    @Column(name = "FECHA_APROBACION")
    private LocalDate fechaAprobacion;

    @Column(name = "APROBACION")
    private String aprobacion;

    @Column(name = "ID_COLABORADOR")
    private String idColaborador;

    @Column(name = "NOMBRE_COLABORADOR")
    private String nombreColaborador;

    @Column(name = "PUNTO_ATENCION")
    private String puntoAtencion;

    @Column(name = "MEDIO_AUTORIZACION")
    private String medioAutorizacion;

    @Column(name = "CODIGO")
    private String codigo;

    @Column(name = "HISTORIA")
    private String historia;

    @Column(name = "CORREO_ENVIADO")
    private String correoEnviado;

@ManyToOne
@JoinColumn(name = "ID_ASIGNACION_HABEAS")
private AsignacionHabeas asignacionHabeas;
}
