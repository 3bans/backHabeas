package com.hptu.interopDllo.adminUsuario.adminUsuario.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hptu.interopDllo.adminUsuario.adminUsuario.Repository.MedicoRepository;
import com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models.Medico;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public List<Medico> obtenerTodosLosMedicos() {
        return medicoRepository.findAll();
    }

    public Medico guardarMedico(Medico medico) {
        return medicoRepository.save(medico);
    }

    public Medico actualizarMedico(Medico medico) {
        return medicoRepository.save(medico); // mismo método sirve para update si ya existe el ID
    }

    public void eliminarMedico(Long idMedico) {
        medicoRepository.deleteById(idMedico);
    }
}
