package com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.request.HabeasRequest;
import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.response.HabeasResponse;
import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.services.HabeasService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/habeas")
@RequiredArgsConstructor
public class HabeasController {

    private final HabeasService habeasService;

@GetMapping("/existe")
public ResponseEntity<Map<String, Object>> existeHabeas(
    @RequestParam("noIdentificacion") String noIdentificacion,
    @RequestParam("tipoId") String tipoId
) {
    log.debug("Entró a GET /habeas/existe con noIdentificacion={} y tipoId={}", noIdentificacion, tipoId);

    try {
        Optional<HabeasResponse> resultado = habeasService.buscarHabeas(noIdentificacion, tipoId);

        Map<String, Object> body = new HashMap<>();

        if (resultado.isPresent()) {
            body.put("code", 200);
            body.put("description", "OK");
            body.put("results", resultado.get());
            return ResponseEntity.ok(body);
        } else {
            body.put("code", 404);
            body.put("description", "No se encontró el paciente con Habeas Data");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        }

    } catch (Exception ex) {
        log.error("Error al verificar existencia de Habeas Data", ex);
        Map<String, Object> error = new HashMap<>();
        error.put("code", 500);
        error.put("description", "Error interno del servidor");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}

    /**
     * POST /habeas/registrar  — recibe un JSON con todos los campos de HabeasRequest
     */
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarHabeas(@RequestBody HabeasRequest req) {
        try {
            Long idGenerado = habeasService.registrar(req);
            if (idGenerado == null) {
                Map<String, Object> body = new HashMap<>();
                body.put("code", 400);
                body.put("description", "No se pudo insertar");
                return ResponseEntity.badRequest().body(body);
            }
            Map<String, Object> body = new HashMap<>();
            body.put("code", 201);
            body.put("description", "Creado");
            body.put("idRegistro", idGenerado);
            return ResponseEntity.status(201).body(body);
        } catch (Exception ex) {
            log.error("Error al registrar Habeas Data", ex);
            Map<String, Object> error = new HashMap<>();
            error.put("code", 500);
            error.put("description", "Error interno");
            return ResponseEntity.status(500).body(error);
        }
    }
}
