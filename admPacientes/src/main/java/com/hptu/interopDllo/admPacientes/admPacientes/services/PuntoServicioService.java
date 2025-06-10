package com.hptu.interopDllo.admPacientes.admPacientes.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.hptu.interopDllo.admPacientes.admPacientes.dto.response.PuntoServicioDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PuntoServicioService {

    private final JdbcTemplate oracleJdbcTemplate;

    /**
     * RowMapper que convierte cada fila en un PuntoServicioDto (sercod, sernom).
     */
    private final RowMapper<PuntoServicioDto> puntoServicioRowMapper = new RowMapper<>() {
        @Override
        public PuntoServicioDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new PuntoServicioDto(
                rs.getString("sercod"),
                rs.getString("sernom")
            );
        }
    };

    /**
     * Ejecuta: SELECT sercod, sernom FROM inser WHERE seract = 'S'
     */
    public List<PuntoServicioDto> obtenerPuntosServicioActivos() {
        String sql = "SELECT sercod, sernom FROM inser WHERE seract = 'S'";
        return oracleJdbcTemplate.query(sql, puntoServicioRowMapper);
    }
}
