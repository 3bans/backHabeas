package com.hptu.interopDllo.habeasData.habeasLogin.controller;

import com.hptu.interopDllo.habeasData.habeasLogin.dto.request.LoginRequest;
import com.hptu.interopDllo.habeasData.habeasLogin.dto.response.ResponseDto;
import com.hptu.interopDllo.habeasData.habeasLogin.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints relacionados con login y generación de token JWT")
public class AuthController {

    private final AuthService authService;
    private final WebClient.Builder webClientBuilder;

    private static final String LDAP_URL = "http://svr-helena:8086/hnsLogin-0.0.1-SNAPSHOT/ldap/ldapHns/";
    private static final String JWT_URL = "http://localhost:3030/authServer/auth/login";

    public AuthController(AuthService authService, WebClient.Builder webClientBuilder) {
        this.authService = authService;
        this.webClientBuilder = webClientBuilder;
    }

    @PostMapping("/login")
    @Operation(
        summary = "Login de usuario y generación de token JWT",
        description = "Valida las credenciales del usuario contra LDAP y si son válidas, genera un JWT usando el authServer.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Login exitoso, token generado",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(
                        name = "Login exitoso",
                        summary = "Respuesta con token generado",
                        value = """
                        {
                          "code": 200,
                          "description": "Login exitoso",
                          "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                        }
                        """
                    )
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Credenciales inválidas",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(
                        name = "Usuario no encontrado",
                        value = """
                        {
                          "code": 404,
                          "description": "Credenciales inválidas",
                          "accessToken": null
                        }
                        """
                    )
                )
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Error interno al generar el token",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(
                        name = "Error en servidor",
                        value = """
                        {
                          "error": "Error generando JWT: Timeout al contactar authServer"
                        }
                        """
                    )
                )
            )
        }
    )
    public ResponseEntity<?> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Credenciales del usuario",
                required = true,
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = LoginRequest.class),
                    examples = @ExampleObject(
                        name = "Ejemplo de credenciales",
                        value = "{ \"user\": \"usuario123\", \"password\": \"secreto\" }"
                    )
                )
            )
            @RequestBody LoginRequest loginRequest) {

        ResponseDto response = authService.callExternalService(LDAP_URL, loginRequest);

        if (response.getCode() != 200) {
            return ResponseEntity.status(404).body(response);
        }

        try {
            Map<String, String> body = new HashMap<>();
            body.put("idUsuario", loginRequest.getUser());

            TokenDto jwtResponse = webClientBuilder.build()
                .post()
                .uri(JWT_URL)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(TokenDto.class)
                .block();

            if (jwtResponse == null || jwtResponse.getAccessToken() == null) {
                return ResponseEntity.status(500).body(Map.of("error", "JWT inválido o respuesta nula del authServer"));
            }

        var userOpt = authService.findUserById(loginRequest.getUser());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "Usuario no registrado en base de datos local"));
        }

        var usuario = userOpt.get();

             Map<String, Object> finalResponse = new HashMap<>();
        finalResponse.put("code", 200);
        finalResponse.put("description", "Login exitoso");
        finalResponse.put("accessToken", jwtResponse.getAccessToken());
        finalResponse.put("nombre", usuario.getNombre());

        return ResponseEntity.ok(finalResponse);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error generando JWT: " + e.getMessage()));
        }
    }

    public static class TokenDto {
        private String accessToken;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }
    }
}
