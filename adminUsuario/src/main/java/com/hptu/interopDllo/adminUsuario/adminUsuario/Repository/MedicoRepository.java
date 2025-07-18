package com.hptu.interopDllo.adminUsuario.adminUsuario.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models.Medico;

@Repository

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findByIdentificacion(String identificacion);
}
