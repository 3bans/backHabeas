package com.hptu.interopDllo.adminUsuario.adminUsuario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models.PuntoServicio;
import com.hptu.interopDllo.adminUsuario.adminUsuario.services.PuntoServicioService;

@RestController
@RequestMapping("puntoServicio")
public class PuntoServicioController {

    private final PuntoServicioService service;

    public PuntoServicioController(PuntoServicioService service) {
        this.service = service;
    }

    @GetMapping("/activos")
    public ResponseEntity<List<PuntoServicio>> obtenerActivos() {
        return ResponseEntity.ok(service.obtenerActivos());
    }
}