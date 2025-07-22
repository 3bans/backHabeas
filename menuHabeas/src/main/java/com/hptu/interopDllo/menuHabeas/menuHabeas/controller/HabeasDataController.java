package com.hptu.interopDllo.menuHabeas.menuHabeas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hptu.interopDllo.menuHabeas.menuHabeas.dto.response.HabeasDataResponse;
import com.hptu.interopDllo.menuHabeas.menuHabeas.service.HabeasDataService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/habeas")
@RequiredArgsConstructor
public class HabeasDataController {
    
    private final HabeasDataService service;

    @GetMapping("/consultar")
    public ResponseEntity<List<HabeasDataResponse>> consultarConFiltros(
            @RequestParam(required = false) String identificacion,
            @RequestParam(required = false) String idMotivo,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin,
            @RequestParam(required = false) String aprobacion
    ) {
        return ResponseEntity.ok(service.obtenerFiltrados(identificacion, idMotivo, fechaInicio, fechaFin, aprobacion));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<HabeasDataResponse>> consultarTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }
}
