package com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabeasResponse {
        private String noIdentificacion;
    private String nombreMedico;
        private String fechaRegistro;
           private String descripcion;
           private String aprobacion;
            private String codigo;
            private String idMedico;
            private String punto;

}
