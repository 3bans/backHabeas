package com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.entity.HasbeasData;


   @Repository
public interface HabeasRepository extends JpaRepository<HasbeasData, Long> {

    Optional<HasbeasData> findByNoIdentificacionAndCodigoAndAprobacion(
        String noIdentificacion,
        String codigo,
        String aprobacion
    );

    
}


