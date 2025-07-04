package com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "PATIENT_REG_HABEAS_DATA")
public class HasbeasData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "ID_ASIGNACION_HABEAS")
    private Long idAsignacionHabeas;
}
