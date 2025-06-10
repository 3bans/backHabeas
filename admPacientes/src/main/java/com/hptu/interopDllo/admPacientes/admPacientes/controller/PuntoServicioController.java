package com.hptu.interopDllo.admPacientes.admPacientes.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hptu.interopDllo.admPacientes.admPacientes.dto.response.PuntoServicioDto;
import com.hptu.interopDllo.admPacientes.admPacientes.services.PuntoServicioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/oracle")
@RequiredArgsConstructor
public class PuntoServicioController {

    private final PuntoServicioService puntoServicioService;

    /**
     * GET /oracle/servicios
     * Retorna lista de puntos de servicio (seract = 'S').
     */
    @GetMapping("/servicios")
    public ResponseEntity<?> listarPuntosServicioActivos() {
        log.debug("Entrando a GET /oracle/servicios");

        try {
            List<PuntoServicioDto> lista = puntoServicioService.obtenerPuntosServicioActivos();
            log.debug("Cantidad de servicios activos: {}", lista.size());

            Map<String, Object> body = new HashMap<>();
            body.put("code", 200);
            body.put("description", "OK");
            body.put("results", lista);
            return ResponseEntity.ok(body);

        } catch (Exception ex) {
            log.error("Error al consultar puntos de servicio en Oracle", ex);
            Map<String, Object> error = new HashMap<>();
            error.put("code", 500);
            error.put("description", "Error interno al obtener servicios");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}