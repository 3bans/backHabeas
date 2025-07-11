package com.hptu.interopDllo.adminUsuario.adminUsuario.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models.PuntoServicio;

@Repository
public interface PuntoServicioRepository extends JpaRepository<PuntoServicio, String> {
    List<PuntoServicio> findByEstado(String estado);
}
