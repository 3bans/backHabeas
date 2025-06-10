package com.hptu.interopDllo.admPacientes.admPacientes.dto.request;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para recibir parámetros de búsqueda de paciente.
 * Ahora con Lombok para generar getters, setters y constructor sin argumentos.
 */
@Data
@NoArgsConstructor
public class PacienteRequest {
    private String TipoId;
    private String NumId;
}