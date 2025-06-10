package com.interopDllo.authServer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interopDllo.authServer.dto.TokenDto;
import com.interopDllo.authServer.dto.UserDto;
import com.interopDllo.authServer.services.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(path = "auth")
@AllArgsConstructor
@Slf4j
@CrossOrigin(
    origins = "http://localhost:4200",
    allowedHeaders = {"Authorization", "Content-Type"},
    exposedHeaders = {"Authorization"},
    methods = {RequestMethod.POST, RequestMethod.OPTIONS},
    allowCredentials = "true" // ✅ Correcto (String)
)
public class AuthController {


    private final AuthService authService;

   @PostMapping("login")
    public ResponseEntity<TokenDto> jwtCreate(@RequestBody UserDto user) {
    return ResponseEntity.ok(this.authService.login(user));
    }
   
    @PostMapping("jwt")
    public ResponseEntity<TokenDto> jwtValidate(@RequestHeader("Authorization") String authorizationHeader) {
        log.info("Authorization Header: {}", authorizationHeader);

        // Verificar que el header no esté vacío o nulo
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(400).body(new TokenDto("Invalid or missing Authorization header"));
        }

        // Extraer solo el token sin "Bearer "
        String token = authorizationHeader.substring(7); // "Bearer " tiene 7 caracteres

        //log.info("Extracted Token: {}", token);

        // Validar el token
        return ResponseEntity.ok(this.authService.validateToken(new TokenDto(token)));
    }
    
}
