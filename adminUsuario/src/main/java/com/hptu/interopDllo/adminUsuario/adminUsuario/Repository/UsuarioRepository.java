package com.hptu.interopDllo.adminUsuario.adminUsuario.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models.Usuarios;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, String> {

    /**
     * Consulta usuarios con su rol y estado legible (usado para visualización en tabla).
     * Esta consulta nativa retorna columnas adicionales que no están en la entidad Usuarios,
     * por eso se mapea a Object[] y luego a UsuarioDTO manualmente.
     */
    @Query(value = """
      SELECT
    u.ID_USUARIO AS idUsuario,
    u.TIPOID_USUARIO AS tipoIdUsuario,
    u.NOMBRE AS nombre,
    u.PUNTO_ATENCION AS puntoAtencion,
    COALESCE(p.ID_PUNTO, 13) AS idPuntoAtencion, 
    u.DEPARTAMENTO AS departamento,
    u.SECCION AS seccion,
    r.NOMBRE_ROL AS nombreRol,
    r.ID_ROL AS rolId,
    CASE 
        WHEN u.ESTADO = 'S' THEN 'ACTIVO'
        WHEN u.ESTADO = 'N' THEN 'INACTIVO'
        ELSE 'Desconocido'
    END AS estado
FROM ADMIN_USUARIO u
LEFT JOIN ADMIN_PUNTO_SERVICIO p ON TRIM(UPPER(p.NOMBRE_PUNTO)) = TRIM(UPPER(u.PUNTO_ATENCION))
INNER JOIN ADMIN_ROL r ON u.ROL_ID = r.ID_ROL;
        """, nativeQuery = true)
    List<Object[]> obtenerUsuariosConRolesRaw();

@Query(value = """
    SELECT * 
    FROM ADMIN_USUARIO 
    WHERE ROL_ID = '5' AND ESTADO = 'Activo'
    """, nativeQuery = true)
List<Usuarios> obtenerUsuariosMedicosActivos();

}
