package com.hptu.interopDllo.admPacientes.admPacientes.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;    
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hptu.interopDllo.admPacientes.admPacientes.dto.response.PacienteResponse;
import com.hptu.interopDllo.admPacientes.admPacientes.services.PacienteService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;



@Slf4j
@RestController
@RequestMapping("/paciente")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPaciente(
            @RequestParam("tipoId") String tipoId,
            @RequestParam("numId") String numId) {

        log.debug("Entrando a GET /paciente/buscar?tipoId={} & numId={}", tipoId, numId);

        try {
            Optional<PacienteResponse> opt = pacienteService.buscarPaciente(tipoId, numId);

            if (opt.isEmpty()) {
                log.debug("No se encontró paciente para tipoId='{}', numId='{}'", tipoId, numId);
                Map<String, Object> body = new HashMap<>();
                body.put("code", 404);
                body.put("description", "Usuario no existe");
                // Devolvemos 404 Not Found con un body JSON
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
            } else {
                PacienteResponse paciente = opt.get();
                log.debug("Paciente encontrado: {}", paciente);
                Map<String, Object> body = new HashMap<>();
                body.put("code", 200);
                body.put("description", "OK");
                body.put("results", paciente);
                return ResponseEntity.ok(body);
            }
        } catch (Exception ex) {
            log.error("Error en buscarPaciente()", ex);
            Map<String, Object> error = new HashMap<>();
            error.put("code", 500);
            error.put("description", "Error interno");
            error.put("results", new Object[0]);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}