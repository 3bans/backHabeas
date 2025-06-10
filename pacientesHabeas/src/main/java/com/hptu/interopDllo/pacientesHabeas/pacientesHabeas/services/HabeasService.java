package com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.services;

import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.request.HabeasRequest;
import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.response.HabeasResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HabeasService {

    // Inyectamos el único JdbcTemplate autoconfigurado
    private final JdbcTemplate jdbcTemplate;

    /**
     * Verifica en SQL Server si existe un registro de Habeas para (noIdentificacion, tipoId).
     * Antes de ejecutar la consulta, elimina cualquier comilla simple al inicio o final de los parámetros.
     */
public Optional<HabeasResponse> buscarHabeas(String noIdentificacion, String tipoId) {
    String sql = """
        SELECT 
            PRHD.NO_IDENTIFICACION, 
            MM.NOMBRE_COMPLETO
        FROM PATIENT_REG_HABEAS_DATA PRHD
        INNER JOIN ASIGNACION_HABEAS AH ON PRHD.ID_ASIGNACION_HABEAS = AH.ID_ASIGNACION
        INNER JOIN MAESTRO_MEDICOS MM ON AH.ID_MEDICO = MM.ID_MEDICO
        WHERE PRHD.TIPO_ID = ? AND PRHD.NO_IDENTIFICACION = ?
    """;

    // Limpieza de comillas
    String tipo = tipoId != null ? tipoId.replaceAll("^'+|'+$", "") : null;
    String doc  = noIdentificacion != null ? noIdentificacion.replaceAll("^'+|'+$", "") : null;

    log.info("Consulta SQL con parámetros:\nTIPO_ID='{}'\nNO_IDENTIFICACION='{}'", tipo, doc);

    List<HabeasResponse> resultados = jdbcTemplate.query(
        sql,
        (rs, rowNum) -> {
            HabeasResponse r = new HabeasResponse();
            r.setNoIdentificacion(rs.getString("NO_IDENTIFICACION"));
            r.setNombreMedico(rs.getString("NOMBRE_COMPLETO"));
            log.debug("Resultado encontrado: {}", r);
            return r;
        },
        tipo, doc
    );

    log.info("Tamaño de resultados: {}", resultados.size());
    return resultados.stream().findFirst();
}

 





    /**
     * Inserta en SQL Server un nuevo registro de HabeasData y devuelve el ID_GENERADO.
     */
    public Long registrar(HabeasRequest req) {
        String sql = """
            INSERT INTO PATIENT_REG_HABEAS_DATA
              (NO_IDENTIFICACION,
               TIPO_ID,
               FECHA_APROBACION,
               APROBACION,
               ID_COLABORADOR,
               NOMBRE_COLABORADOR,
               PUNTO_ATENCION,
               MEDIO_AUTORIZACION,
               CODIGO,
               HISTORIA,
               CORREO_ENVIADO)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        // Convertir fecha (String “yyyy-MM-dd”) a java.sql.Date
        Date fechaSql;
        try {
            fechaSql = Date.valueOf(req.getFechaAprobacion());
        } catch (IllegalArgumentException ex) {
            log.error("Formato inválido para fechaAprobacion: {}", req.getFechaAprobacion(), ex);
            throw ex;
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int filasAfectadas = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // NOTA: aquí asumimos que en la petición JSON no vendrán con comillas, 
            //       pero si quieres también limpiar en registrar(), puedes usar el mismo replaceAll.
            ps.setString(1, req.getNoIdentificacion());
            ps.setString(2, req.getTipoId());
            ps.setDate(3, fechaSql);
            ps.setBoolean(4, Boolean.TRUE.equals(req.getAprobacion()));
            ps.setString(5, req.getIdColaborador());
            ps.setString(6, req.getNombreColaborador());
            ps.setString(7, req.getPuntoAtencion());
            ps.setString(8, req.getMedioAutorizacion());
            ps.setString(9, req.getCodigo());
            ps.setString(10, req.getHistoria());
            ps.setString(11, req.getCorreoEnviado());
            return ps;
        }, keyHolder);

        if (filasAfectadas == 0) {
            return null;
        }
        Number generatedKey = keyHolder.getKey();
        return (generatedKey != null) ? generatedKey.longValue() : null;
    }

    // (Se ha eliminado cualquier método que usara oracleJdbcTemplate)
}
