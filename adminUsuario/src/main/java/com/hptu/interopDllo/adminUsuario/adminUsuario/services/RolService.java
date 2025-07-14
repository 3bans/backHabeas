package com.hptu.interopDllo.adminUsuario.adminUsuario.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hptu.interopDllo.adminUsuario.adminUsuario.Repository.RolRepository;
import com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models.Rol;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class RolService {
        private final RolRepository repository;

  

    public List<Rol> obtenerActivos() {
        return repository.findByEstado("S");
    }
}
