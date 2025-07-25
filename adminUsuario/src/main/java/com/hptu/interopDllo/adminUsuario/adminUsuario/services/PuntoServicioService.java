package com.hptu.interopDllo.adminUsuario.adminUsuario.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hptu.interopDllo.adminUsuario.adminUsuario.Repository.PuntoServicioRepository;
import com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models.PuntoServicio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PuntoServicioService {

    private final PuntoServicioRepository repository;

   

    public List<PuntoServicio> obtenerActivos() {
        return repository.findByEstado("A");
    }
}