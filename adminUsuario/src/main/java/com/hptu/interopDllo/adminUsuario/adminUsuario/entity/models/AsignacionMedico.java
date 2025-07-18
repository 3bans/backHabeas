package com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ASIGNACION_MEDICOS", schema = "dbo", catalog = "Sata")
@Data
public class AsignacionMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ASIGNACION")
    private Long idAsignacion;

    @Column(name = "ID_USUARIO", nullable = false)
    private String idUsuario;

    @Column(name = "ID_MEDICO", nullable = false)
    private Long idMedico;
}
