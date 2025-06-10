package com.hptu.interopDllo.admPacientes.admPacientes.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hptu.interopDllo.admPacientes.admPacientes.dto.response.PacienteResponse;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PacienteService {

    // Único JdbcTemplate autoconfigurado por Spring Boot (apunta a Oracle)
    private final JdbcTemplate jdbcTemplate;

   public Optional<PacienteResponse> buscarPaciente(String tipoId, String numId) {
    // 1) Limpiar comillas simples al inicio y al final
    String tipo = (tipoId   == null) ? null : tipoId.replaceAll("^'+|'+$", "");
    String numero = (numId == null) ? null : numId.replaceAll("^'+|'+$", "");

    String sql = """
        SELECT 
          PACIDE, PACTID, PACAP1, PACAP2, 
          TO_CHAR(PACNAC,'dd/MM/yyyy') AS FEC_NAC, 
          PACHIS, CONCAT(CONCAT(PACNOM,' '), PACN2B) AS NOMBRES, 
          PACCEL, PACTEL, PACCOE
        FROM ABPAC
        WHERE PACTID = ? AND PACIDE = ?
        """;

    try {
        PacienteResponse pr = jdbcTemplate.queryForObject(
            sql,
            new Object[]{ tipo, numero },
            (rs, rowNum) -> {
                PacienteResponse r = new PacienteResponse();
                r.setNumPaciente(     rs.getString(1));
                r.setDocPaciente(     rs.getString(2));
                r.setApellido1(       rs.getString(3));
                r.setApellido2(       rs.getString(4));
                r.setFechaNacimiento( rs.getString(5));
                r.setHistoria(        rs.getString(6));
                r.setNombres(         rs.getString(7));
                r.setCelular(         rs.getString(8));
                r.setTelefono(        rs.getString(9));
                r.setCorreo(          rs.getString(10));
                return r;
            }
        );
        return Optional.of(pr);
    } catch (EmptyResultDataAccessException ex) {
        return Optional.empty();
    }
}}