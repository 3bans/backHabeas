package com.hptu.interopDllo.habeasData.habeasLogin.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank; // Jakarta Validation en Spring Boot 3
import io.swagger.v3.oas.annotations.media.Schema;


@Data
@Schema(description = "Objeto de solicitud de login")

public class LoginRequest {
    @NotBlank
    @Schema(description = "Usuario del sistema", example = "usuario123", required = true)

    private String user;

    @NotBlank
    @Schema(description = "Contraseña del usuario", example = "secreto", required = true)

    private String password;
}


