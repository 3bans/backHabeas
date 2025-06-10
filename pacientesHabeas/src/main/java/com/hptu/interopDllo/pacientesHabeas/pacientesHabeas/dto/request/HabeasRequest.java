package com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para recibir el body de la petición de registro en PATIENT_REG_HABEAS_DATA.
 */
@Data
@NoArgsConstructor
public class HabeasRequest {
    private String noIdentificacion;    // NO_IDENTIFICACION
    private String tipoId;              // TIPO_ID
    private String fechaAprobacion;     // FECHA_APROBACION (Formato 'yyyy-MM-dd' o similar)
    private Boolean aprobacion;         // APROBACION (true/false)
    private String idColaborador;       // ID_COLABORADOR
    private String nombreColaborador;   // NOMBRE_COLABORADOR
    private String puntoAtencion;       // PUNTO_ATENCION
    private String medioAutorizacion;   // MEDIO_AUTORIZACION
    private String codigo;              // CODIGO
    private String historia;            // HISTORIA
    private String correoEnviado;       // CORREO_ENVIADO
}
