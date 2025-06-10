package com.hptu.interopDllo.admPacientes.admPacientes.services.implement;

import org.springframework.jdbc.core.RowMapper;

import com.hptu.interopDllo.admPacientes.admPacientes.dto.response.PacienteResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PacienteRowMapper implements RowMapper<PacienteResponse> {
    @Override
    public PacienteResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        PacienteResponse pr = new PacienteResponse();
        pr.setNumPaciente(rs.getString(1));        // PACIDE
        pr.setDocPaciente(rs.getString(2));        // PACTID
        pr.setApellido1(rs.getString(3));          // PACAP1
        pr.setApellido2(rs.getString(4));          // PACAP2
        pr.setFechaNacimiento(rs.getString(5));    // FEC_NAC
        pr.setHistoria(rs.getString(6));           // PACHIS
        pr.setNombres(rs.getString(7));            // NOMBRES
        pr.setTelefono(rs.getString(9));           // PACTEL
        pr.setCelular(rs.getString(8));            // PACCEL
        pr.setCorreo(rs.getString(10));            // PACCOE
        return pr;
    }
}