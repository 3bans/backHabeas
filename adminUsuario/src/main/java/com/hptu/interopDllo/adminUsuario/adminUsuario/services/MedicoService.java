package com.hptu.interopDllo.adminUsuario.adminUsuario.services;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.hptu.interopDllo.adminUsuario.adminUsuario.Repository.MedicoRepository;
import com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models.Medico;
import com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models.Usuarios;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class MedicoService {

    private final MedicoRepository medicoRepository;

    public List<Medico> obtenerTodosLosMedicos() {
        return medicoRepository.findAll();
    }

    public Medico guardarMedico(Medico medico) {
        Optional<Medico> existente = medicoRepository.findByIdentificacion(medico.getIdentificacion());
        if (existente.isPresent()) {
            throw new DataIntegrityViolationException("Ya existe un médico con la identificación: " + medico.getIdentificacion());
        }
        return medicoRepository.save(medico);
    }

    public Medico actualizarMedico(Medico medico) {
        if (medico.getIdMedico() == null) {
            throw new IllegalArgumentException("ID del médico no puede ser null para actualizar.");
        }

        Optional<Medico> existente = medicoRepository.findById(medico.getIdMedico());
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("No se encontró el médico con ID: " + medico.getIdMedico());
        }

        Optional<Medico> duplicado = medicoRepository.findByIdentificacion(medico.getIdentificacion());
        if (duplicado.isPresent() && !duplicado.get().getIdMedico().equals(medico.getIdMedico())) {
            throw new DataIntegrityViolationException("La identificación ya pertenece a otro médico.");
        }

        return medicoRepository.save(medico);
    }

    public void eliminarMedico(Long idMedico) {
        if (!medicoRepository.existsById(idMedico)) {
            throw new IllegalArgumentException("No se encontró el médico con ID: " + idMedico);
        }
        medicoRepository.deleteById(idMedico);
    }
    
}