
package com.hptu.interopDllo.adminUsuario.adminUsuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private String idUsuario;
    private String tipoIdUsuario;
    private String nombre;
    private String puntoAtencion;
    private String departamento;
    private String seccion;
    private String nombreRol;
    private String estado;
}
