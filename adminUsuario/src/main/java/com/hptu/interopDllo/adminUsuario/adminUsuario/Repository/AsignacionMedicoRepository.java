package com.hptu.interopDllo.adminUsuario.adminUsuario.Repository;

import com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models.AsignacionMedico;
import com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models.Medico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignacionMedicoRepository extends JpaRepository<AsignacionMedico, Long> {

    List<AsignacionMedico> findByIdUsuario(String idUsuario);

    void deleteByIdUsuario(String idUsuario);
    void deleteByIdUsuarioAndIdMedico(String idUsuario, Long idMedico); // ← elimina uno específico

    @Query("""
        SELECT m
        FROM AsignacionMedico a
        JOIN Medico m ON a.idMedico = m.idMedico
        WHERE a.idUsuario = :idUsuario
    """)
    List<Medico> findMedicosAsignadosByUsuario(@Param("idUsuario") String idUsuario);
}