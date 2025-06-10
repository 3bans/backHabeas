package com.hptu.interopDllo.admPacientes.admPacientes.dto.response;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PacienteResponse {
    private String numPaciente;
    private String docPaciente;
    private String apellido1;
    private String apellido2;
    private String fechaNacimiento;
    private String historia;
    private String nombres;
    private String telefono;
    private String celular;
    private String correo;
}