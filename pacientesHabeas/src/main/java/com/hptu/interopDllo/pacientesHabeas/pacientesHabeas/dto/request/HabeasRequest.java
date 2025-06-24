package com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para recibir el body de la petición de registro en PATIENT_REG_HABEAS_DATA.
 */
@Data
@NoArgsConstructor
public class HabeasRequest {

    // Para ASIGNACION_HABEAS
    private Long idMedico;
    private Long idAplicacion;
    private Integer idMotivo;   // nulo si aprobó

    // Para PATIENT_REG_HABEAS_DATA
    private String noIdentificacion;
    private String tipoId;
    private String fechaAprobacion;    // "yyyy-MM-dd" opcional
    private String aprobacion;
    private String idColaborador;
    private String nombreColaborador;
    private String puntoAtencion;
    private String medioAutorizacion;
    private String codigo;
    private String historia;
    private String correoEnviado;
}

