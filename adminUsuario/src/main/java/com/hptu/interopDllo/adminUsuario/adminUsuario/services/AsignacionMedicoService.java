package com.hptu.interopDllo.adminUsuario.adminUsuario.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hptu.interopDllo.adminUsuario.adminUsuario.Repository.AsignacionMedicoRepository;
import com.hptu.interopDllo.adminUsuario.adminUsuario.dto.request.AsignacionMedicoRequest;
import com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models.AsignacionMedico;
import com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models.Medico;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsignacionMedicoService {

    private final AsignacionMedicoRepository repository;

    @Transactional
    public void asignarMedicos(AsignacionMedicoRequest request) {
      
        // Crear nuevas asignaciones
        List<AsignacionMedico> asignaciones = request.getIdMedicos().stream().map(idMedico -> {
            AsignacionMedico asignacion = new AsignacionMedico();
            asignacion.setIdUsuario(request.getIdUsuario());
            asignacion.setIdMedico(idMedico);
            return asignacion;
        }).toList();

        repository.saveAll(asignaciones);
    }

    public List<AsignacionMedico> obtenerAsignacionesPorUsuario(String idUsuario) {
        return repository.findByIdUsuario(idUsuario);
    }


   public List<Medico> obtenerMedicosAsignados(String idUsuario) {
    return repository.findMedicosAsignadosByUsuario(idUsuario);
}
  @Transactional
public void eliminarAsignacion(String idUsuario, Long idMedico) {
    repository.deleteByIdUsuarioAndIdMedico(idUsuario, idMedico);
}
}