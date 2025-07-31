package com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.services;

import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.request.EmailRequest;
import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.request.HabeasRequest;
import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.request.mensajeRequest;
import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.response.HabeasResponse;
import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.response.ListaMedicosResponse;
import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.dto.response.MotivosaHabeas;
import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.entity.HasbeasData;
import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.repository.HabeasRepository;

import java.sql.Timestamp;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.scheduling.annotation.Async;
import java.sql.Types;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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

public List<HabeasResponse> buscarHabeas(String noIdentificacion, String tipoId,  String aplicacion) {
    String sql = """
        SELECT PRHD.NO_IDENTIFICACION, MM.NOMBRE_COMPLETO, AH.FECHA_REGISTRO, 
               MAESTRO_MOTIVOS_HABEAS.DESCRIPCION, PRHD.APROBACION,PRHD.CODIGO, MM.ID_MEDICO
        FROM PATIENT_REG_HABEAS_DATA AS PRHD
        INNER JOIN ASIGNACION_HABEAS AS AH ON PRHD.ID_ASIGNACION_HABEAS = AH.ID_ASIGNACION
        LEFT JOIN MAESTRO_MEDICOS AS MM ON AH.ID_MEDICO = MM.ID_MEDICO
        INNER JOIN MAESTRO_MOTIVOS_HABEAS ON AH.ID_MOTIVO = MAESTRO_MOTIVOS_HABEAS.ID_MOTIVO
        WHERE PRHD.TIPO_ID = ? AND PRHD.NO_IDENTIFICACION = ? AND AH.ID_APLICACION=?
    """;

    // Ya no se limpian comillas, solo espacios
    String tipo = tipoId != null ? tipoId.trim() : null;
    String doc  = noIdentificacion != null ? noIdentificacion.trim() : null;
    String app  = aplicacion != null ? aplicacion.trim() : null;
System.err.println(sql);
System.err.println(tipo+' '+doc+' '+app);

    return jdbcTemplate.query(
        sql,
        (rs, rowNum) -> {
            HabeasResponse r = new HabeasResponse();
            r.setNoIdentificacion(rs.getString("NO_IDENTIFICACION"));
            r.setNombreMedico(rs.getString("NOMBRE_COMPLETO"));
            r.setFechaRegistro(rs.getString("FECHA_REGISTRO"));
            r.setDescripcion(rs.getString("DESCRIPCION"));
            r.setAprobacion(rs.getString("APROBACION"));
             r.setCodigo(rs.getString("CODIGO"));
                r.setIdMedico(rs.getString("ID_MEDICO"));
            return r;
        },
        doc,tipo,app
    );
}



@Transactional
public Long registrar(HabeasRequest req) {
    log.info("📥 Parámetros recibidos en HabeasRequest: {}", req);

    // Validación mínima (puedes agregar más si deseas)
    if ( req.getIdAplicacion() == null) {
        throw new IllegalArgumentException("❌ idMedico e idAplicacion son obligatorios.");
    }

    // 1. INSERT en ASIGNACION_HABEAS
    String sqlAsig = """
        INSERT INTO ASIGNACION_HABEAS
        ( ID_HABEAS, ID_MEDICO, ID_APLICACION, ID_MOTIVO,FECHA_REGISTRO )
        VALUES (?, ?, ?, ?,?)
    """;

    KeyHolder asigHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(conn -> {
        PreparedStatement ps = conn.prepareStatement(sqlAsig, Statement.RETURN_GENERATED_KEYS);
          ps.setLong(1, 7); // ID_HABEAS

    // ————————————————
    // Aquí controlamos idMedico nulo
    if (req.getIdMedico() != null) {
        ps.setLong(2, req.getIdMedico());
    } else {
        ps.setNull(2, Types.BIGINT);
    }
    // ————————————————

    // ID_APLICACION (obligatorio)
    ps.setLong(3, req.getIdAplicacion());

    // ID_MOTIVO (puede ser nulo)
    if (req.getIdMotivo() != null) {
        ps.setInt(4, req.getIdMotivo());
    } else {
        ps.setNull(4, Types.INTEGER);
    }

    ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
    return ps;

    }, asigHolder);

    Number keyAsig = asigHolder.getKey();
    if (keyAsig == null) {
        throw new DataAccessException("❌ Fallo al generar ID_ASIGNACION_HABEAS") {};
    }

    int idAsignacion = keyAsig.intValue();
    log.info("✅ ID_ASIGNACION_HABEAS generado: {}", idAsignacion);

    // 2. INSERT en PATIENT_REG_HABEAS_DATA
    String sqlReg = """
        INSERT INTO PATIENT_REG_HABEAS_DATA
        ( NO_IDENTIFICACION, TIPO_ID, FECHA_APROBACION, APROBACION,
          ID_COLABORADOR, NOMBRE_COLABORADOR, PUNTO_ATENCION,
          MEDIO_AUTORIZACION, CODIGO, HISTORIA, CORREO_ENVIADO,
          ID_ASIGNACION_HABEAS )
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

    String fechaStr = (req.getFechaAprobacion() != null && !req.getFechaAprobacion().isBlank())
        ? req.getFechaAprobacion()
        : LocalDate.now().toString();

    Date fechaSql = Date.valueOf(fechaStr);

    KeyHolder regHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(conn -> {
        PreparedStatement ps = conn.prepareStatement(sqlReg, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, req.getNoIdentificacion());
        ps.setString(2, req.getTipoId());
        ps.setDate(3, fechaSql);
        ps.setString(4, req.getAprobacion() != null ? req.getAprobacion() : "N");
        ps.setString(5, req.getIdColaborador());
        ps.setString(6, req.getNombreColaborador());
        ps.setString(7, req.getPuntoAtencion());
        ps.setString(8, req.getMedioAutorizacion());
        ps.setString(9, req.getCodigo());
        ps.setString(10, req.getHistoria());
        ps.setString(11, req.getCorreoEnviado());
        ps.setInt(12, idAsignacion);
        return ps;
    }, regHolder);

    Number keyReg = regHolder.getKey();
    log.info("✅ ID_REGISTRO generado: {}", keyReg);

    return (keyReg != null) ? keyReg.longValue() : null;
}



  public List<ListaMedicosResponse> buscarMedicosPorUsuario(String idUsuario, String idPaciente, String aprobacion) {
        String sql = """
    
SELECT       MAESTRO_MEDICOS.ID_MEDICO,  MAESTRO_MEDICOS.NOMBRE_COMPLETO
FROM            ASIGNACION_MEDICOS INNER JOIN
                         MAESTRO_MEDICOS ON ASIGNACION_MEDICOS.ID_MEDICO = MAESTRO_MEDICOS.ID_MEDICO
						 WHERE  ASIGNACION_MEDICOS.ID_USUARIO=? AND MAESTRO_MEDICOS.ID_MEDICO NOT IN  (
    SELECT AM.ID_MEDICO
FROM ASIGNACION_HABEAS AM
INNER JOIN PATIENT_REG_HABEAS_DATA PRHD ON AM.ID_ASIGNACION = PRHD.ID_ASIGNACION_HABEAS
WHERE PRHD.NO_IDENTIFICACION = ? and PRHD.APROBACION= ?)
        """;
  String doc  = idUsuario != null ? idUsuario.replaceAll("^'+|'+$", "") : null;
  String idPacientes =idPaciente != null ? idPaciente.replaceAll("^'+|'+$", "") : null;
    String aprobacions =aprobacion != null ? aprobacion.replaceAll("^'+|'+$", "") : null;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ListaMedicosResponse dto = new ListaMedicosResponse();
            dto.setNoIdentificacion(rs.getString("ID_MEDICO"));
            dto.setNombreMedico(rs.getString("NOMBRE_COMPLETO"));
            //log.debug("✅ Médico encontrado: {}", dto);
            return dto;
        }, doc,idPacientes,aprobacions);
    }


      public List<MotivosaHabeas> cargarListaMotivosaHabeas() {
        String sql = """
       select ID_MOTIVO,DESCRIPCION,ESTADO from MAESTRO_MOTIVOS_HABEAS where ESTADo='Activo'
        """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            MotivosaHabeas dto = new MotivosaHabeas();
            dto.setDescripcion(rs.getString("DESCRIPCION"));
            dto.setId_motivo(rs.getString("ID_MOTIVO"));
            dto.setEstado(rs.getString("ESTADO"));
            //log.debug("✅ Médico encontrado: {}", dto);
            return dto;
        });
    }

     private static final String MIRTH_URL = "http://192.168.10.6:1001/ws_smsHabeas/";

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> reenviarSms(mensajeRequest mensaje) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<mensajeRequest> request = new HttpEntity<>(mensaje, headers);

        return restTemplate.exchange(MIRTH_URL, HttpMethod.POST, request, String.class);
    }


    private final HabeasRepository habeasRepository;


public boolean validarYActualizar(String noIdentificacion, String codigo) {
    if (noIdentificacion == null || codigo == null) return false;

    // Normaliza los datos
    String doc = noIdentificacion.trim();
    String cod = codigo.trim();

    log.info("🧪 Validando para doc={} y codigo={}", doc, cod);

    Optional<HasbeasData> registroOpt =
        habeasRepository.findByNoIdentificacionAndCodigoAndAprobacion(doc, cod, "P");

    if (registroOpt.isPresent()) {
        HasbeasData registro = registroOpt.get();
        registro.setAprobacion("S");
        registro.setFechaAprobacion(LocalDate.now());
        habeasRepository.save(registro);
        return true;
    }

    return false;
}

   private static final String MIRTH_EMAIL_URL = "http://192.168.10.6:9111/ws_EmailHabeas/";


    @Async
    public CompletableFuture<String> enviarCorreoAsync(EmailRequest mensaje) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<EmailRequest> request = new HttpEntity<>(mensaje, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                MIRTH_EMAIL_URL,
                HttpMethod.POST,
                request,
                String.class
            );

            return CompletableFuture.completedFuture("✅ Correo enviado: " + response.getBody());

        } catch (Exception e) {
            return CompletableFuture.completedFuture("❌ Error al enviar correo: " + e.getMessage());
        }
    }


}
