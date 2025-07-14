package com.hptu.interopDllo.adminUsuario.adminUsuario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models.Rol;
import com.hptu.interopDllo.adminUsuario.adminUsuario.services.RolService;

@RestController
@RequestMapping("/roles")
public class RolController {

    private final RolService service;

    public RolController(RolService service) {
        this.service = service;
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Rol>> obtenerActivos() {
        return ResponseEntity.ok(service.obtenerActivos());
    }
}