package com.hptu.interopDllo.menuHabeas.menuHabeas.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;


import io.swagger.v3.oas.annotations.media.Schema;


@Data
@AllArgsConstructor
@Schema(description = "Aplicación asignada al usuario")
public class AppResponse {

    @Schema(description = "ID de la aplicación", example = "1")
    private Long id;

    @Schema(description = "Nombre de la aplicación", example = "Mi App")
    private String nombre;

    @Schema(description = "Descripción de la aplicación", example = "Acceso al portal")
    private String descripcion;

    @Schema(description = "URL de acceso", example = "https://miapp.com")
    private String url;
}
