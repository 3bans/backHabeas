package com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsentRequest {
    private String profesionalNombre;
    private String profesionalDocumento;
    private int dia;
    private String mes;
    private int ano;
    private String pacienteNombre;
    private String pacienteTipoDocumento;
    private String pacienteDocumento;
    private String representanteNombre;
    private String representanteTipoDocumento;
    private String representanteDocumento;
}
