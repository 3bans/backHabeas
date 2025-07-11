package com.hptu.interopDllo.adminUsuario.adminUsuario.services;

import java.util.List;

import com.hptu.interopDllo.adminUsuario.adminUsuario.Repository.RolRepository;
import com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models.Rol;

public class RolService {
        private final RolRepository repository;

    public RolService(RolRepository repository) {
        this.repository = repository;
    }

    public List<Rol> obtenerActivos() {
        return repository.findByEstado("S");
    }
}
