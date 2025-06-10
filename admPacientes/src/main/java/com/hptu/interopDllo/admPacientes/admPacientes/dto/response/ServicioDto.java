package com.hptu.interopDllo.admPacientes.admPacientes.dto.response;

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