package com.hptu.interopDllo.adminUsuario.adminUsuario.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    // Puedes agregar métodos custom si lo necesitas, por ahora hereda todos
}
