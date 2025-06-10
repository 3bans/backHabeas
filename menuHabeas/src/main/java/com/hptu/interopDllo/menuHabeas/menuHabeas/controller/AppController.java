package com.hptu.interopDllo.menuHabeas.menuHabeas.controller;

import com.hptu.interopDllo.menuHabeas.menuHabeas.dto.response.AppResponse;
import com.hptu.interopDllo.menuHabeas.menuHabeas.service.AppService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/apps")
@Tag(name = "Aplicaciones", description = "Operaciones relacionadas con aplicaciones del usuario")
public class AppController {

    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping("/{userId}")
    @Operation(
        summary = "Obtener aplicaciones activas por usuario",
        description = "Retorna la lista de aplicaciones activas asignadas a un usuario.",
        parameters = {
            @Parameter(
                name = "userId",
                description = "ID del usuario",
                required = true,
                example = "123"
            )
        },
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Listado de aplicaciones activas",
                content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = AppResponse.class)),
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                        [
                          {
                            "id": 1,
                            "nombre": "Mi App",
                            "descripcion": "Portal de gestión médica",
                            "url": "https://app.hptu.org"
                          },
                          {
                            "id": 2,
                            "nombre": "Intranet",
                            "descripcion": "Sistema interno para colaboradores",
                            "url": "https://intranet.hptu.org"
                          }
                        ]
                        """
                    )
                )
            ),
            @ApiResponse(
                responseCode = "401",
                description = "El usuario no tiene permisos o el token es inválido",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        value = """
                        {
                          "code": 401,
                          "message": "Token inválido o el usuario no tiene permisos en esta aplicación"
                        }
                        """
                    )
                )
            )
        }
    )
    public ResponseEntity<?> getApps(@PathVariable String userId) {
        List<AppResponse> apps = appService.obtenerAplicacionesPorUsuario(userId);
        if (apps.isEmpty()) {
            return ResponseEntity.status(401).body(
                Map.of(
                    "code", 401,
                    "message", "Token inválido o el usuario no tiene permisos en esta aplicación"
                )
            );
        }
        return ResponseEntity.ok(apps);
    }
}
