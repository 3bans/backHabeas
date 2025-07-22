package com.hptu.interopDllo.menuHabeas.menuHabeas.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hptu.interopDllo.menuHabeas.menuHabeas.dto.response.HabeasDataResponse;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class HabeasDataRepository {

    @PersistenceContext
    private EntityManager em;
public List<HabeasDataResponse> consultarConFiltros(String identificacionMedico, String idMotivo,
                                                    String fechaInicio, String fechaFin, String aprobacion) {
    StringBuilder sql = new StringBuilder("""
       SELECT new com.hptu.interopDllo.menuHabeas.menuHabeas.dto.response.HabeasDataResponse(
            prhd.idRegistro, prhd.noIdentificacion, prhd.tipoId, prhd.fechaAprobacion,
            prhd.aprobacion, prhd.idColaborador, prhd.nombreColaborador, prhd.puntoAtencion,
            prhd.medioAutorizacion, prhd.codigo, prhd.historia, prhd.correoEnviado,
            prhd.asignacionHabeas.medico.nombreCompleto,
            prhd.asignacionHabeas.motivo.descripcion
        )
        FROM PatientRegHabeasData prhd
        WHERE 1 = 1
    """);

    if (identificacionMedico != null && !identificacionMedico.isBlank()) {
        sql.append(" AND prhd.asignacionHabeas.medico.identificacion = :identificacionMedico");
    }
    if (idMotivo != null && !idMotivo.isBlank()) {
        sql.append(" AND prhd.asignacionHabeas.motivo.idMotivo = :idMotivo");
    }
    if (fechaInicio != null && fechaFin != null && !fechaInicio.isBlank() && !fechaFin.isBlank()) {
        sql.append(" AND prhd.asignacionHabeas.fechaRegistro BETWEEN :fechaInicio AND :fechaFin");
    }
    if (aprobacion != null && !aprobacion.isBlank()) {
        sql.append(" AND prhd.aprobacion = :aprobacion");
    }

    var query = em.createQuery(sql.toString(), HabeasDataResponse.class);

    if (identificacionMedico != null && !identificacionMedico.isBlank()) {
        query.setParameter("identificacionMedico", identificacionMedico);
    }
    if (idMotivo != null && !idMotivo.isBlank()) {
        query.setParameter("idMotivo", idMotivo);
    }
    if (fechaInicio != null && fechaFin != null && !fechaInicio.isBlank() && !fechaFin.isBlank()) {
        query.setParameter("fechaInicio", java.sql.Date.valueOf(fechaInicio));
        query.setParameter("fechaFin", java.sql.Date.valueOf(fechaFin));
    }
    if (aprobacion != null && !aprobacion.isBlank()) {
        query.setParameter("aprobacion", aprobacion);
    }

    return query.getResultList();
}

 public List<HabeasDataResponse> consultarTodos() {
    return em.createQuery("""
        SELECT new com.hptu.interopDllo.menuHabeas.menuHabeas.dto.response.HabeasDataResponse(
            prhd.idRegistro, prhd.noIdentificacion, prhd.tipoId, prhd.fechaAprobacion,
            prhd.aprobacion, prhd.idColaborador, prhd.nombreColaborador, prhd.puntoAtencion,
            prhd.medioAutorizacion, prhd.codigo, prhd.historia, prhd.correoEnviado,
            prhd.asignacionHabeas.medico.nombreCompleto,
            prhd.asignacionHabeas.motivo.descripcion
        )
        FROM PatientRegHabeasData prhd
    """, HabeasDataResponse.class).getResultList();
}
}
