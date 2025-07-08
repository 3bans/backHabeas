package com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailRequest {
    private String codigo;
    private String emailpac;
     private String paciente;

}
