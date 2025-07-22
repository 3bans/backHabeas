package com.hptu.interopDllo.adminUsuario.adminUsuario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hptu.interopDllo.adminUsuario.adminUsuario.dto.request.AsignacionMedicoRequest;
import com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models.AsignacionMedico;
import com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models.Medico;
import com.hptu.interopDllo.adminUsuario.adminUsuario.services.AsignacionMedicoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/asignacionMedicos")
@RequiredArgsConstructor
public class AsignacionMedicoController {

    private final AsignacionMedicoService service;
    @PostMapping("/asignar")
    public ResponseEntity<Void> asignarMedicos(@RequestBody AsignacionMedicoRequest request) {
        service.asignarMedicos(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<AsignacionMedico>> obtenerAsignaciones(@PathVariable String idUsuario) {
        return ResponseEntity.ok(service.obtenerAsignacionesPorUsuario(idUsuario));
    }
   @GetMapping("/medicoSecretaria/{idUsuario}")
    public ResponseEntity<List<Medico>> obtenerMedicosAsignados(
            @PathVariable("idUsuario") String idUsuario
    ) {
        List<Medico> medicos = service.obtenerMedicosAsignados(idUsuario);
        return ResponseEntity.ok(medicos);
    }

  @DeleteMapping("/secretariAsignada/{idUsuario}/{idMedico}")
public ResponseEntity<Void> eliminarAsignacionMedico(
        @PathVariable String idUsuario,
        @PathVariable Long idMedico
) {
    service.eliminarAsignacion(idUsuario, idMedico);
    return ResponseEntity.noContent().build();
}
    
}
