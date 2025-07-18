package com.hptu.interopDllo.adminUsuario.adminUsuario.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MedicoAsignadoResponse {
    private Long idMedico;
    private String identificacion;
    private String nombreCompleto;
    private String especialidad;
    private String consultorio;
    private String estado;
}
