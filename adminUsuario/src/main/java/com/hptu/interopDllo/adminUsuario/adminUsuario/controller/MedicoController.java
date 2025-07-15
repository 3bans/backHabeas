package com.hptu.interopDllo.adminUsuario.adminUsuario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models.Medico;
import com.hptu.interopDllo.adminUsuario.adminUsuario.services.MedicoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService medicoService;

    @GetMapping("/activos")
    public ResponseEntity<List<Medico>> listarMedicos() {
        return ResponseEntity.ok(medicoService.obtenerTodosLosMedicos());
    }

    @PostMapping("/registrar")
    public ResponseEntity<Medico> crearMedico(@RequestBody Medico medico) {
        return ResponseEntity.ok(medicoService.guardarMedico(medico));
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Medico> actualizarMedico(@RequestBody Medico medico) {
        return ResponseEntity.ok(medicoService.actualizarMedico(medico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMedico(@PathVariable Long id) {
        medicoService.eliminarMedico(id);
        return ResponseEntity.noContent().build();
    }
}