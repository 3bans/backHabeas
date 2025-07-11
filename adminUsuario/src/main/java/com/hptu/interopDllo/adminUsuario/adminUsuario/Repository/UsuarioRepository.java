package com.hptu.interopDllo.adminUsuario.adminUsuario.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models.Usuarios;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, String> {

    /**
     * Consulta usuarios con roles y estado legible.
     */
    @Query(value = """
        SELECT
            u.ID_USUARIO as idUsuario,
            u.TIPOID_USUARIO as tipoIdUsuario,
            u.NOMBRE as nombre,
            u.PUNTO_ATENCION as puntoAtencion,
            u.DEPARTAMENTO as departamento,
            u.SECCION as seccion,
            r.NOMBRE_ROL as nombreRol,
            CASE 
                WHEN u.ESTADO = 'S' THEN 'ACTIVO'
                WHEN u.ESTADO = 'N' THEN 'INACTIVO'
                ELSE 'Desconocido'
            END as estado
        FROM ADMIN_USUARIO u
        INNER JOIN ADMIN_ROL r ON u.ROL_ID = r.ID_ROL
        """, nativeQuery = true)
    List<Object[]> obtenerUsuariosConRolesRaw();

    // ✅ Ya puedes usar: save(Usuarios usuario)
}
