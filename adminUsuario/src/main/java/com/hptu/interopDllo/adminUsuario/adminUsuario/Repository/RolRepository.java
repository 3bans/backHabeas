package com.hptu.interopDllo.adminUsuario.adminUsuario.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, String> {
    List<Rol> findByEstado(String estado);
}
