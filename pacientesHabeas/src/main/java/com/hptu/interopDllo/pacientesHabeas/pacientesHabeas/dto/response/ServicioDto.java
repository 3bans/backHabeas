package com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioDto {
    private String codigo;   // Código del servicio
    private String nombre;   // Nombre del servicio
}