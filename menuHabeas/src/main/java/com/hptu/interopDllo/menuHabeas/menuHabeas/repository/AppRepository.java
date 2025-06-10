package com.hptu.interopDllo.menuHabeas.menuHabeas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hptu.interopDllo.menuHabeas.menuHabeas.dto.response.AppProjection;
import com.hptu.interopDllo.menuHabeas.menuHabeas.entity.AppEntity;

import java.util.List;


public interface AppRepository extends JpaRepository<AppEntity, Long> {

  @Query(value = """
    SELECT 
        A.ID_APP AS id,
        A.NOMBRE_APP AS nombre,
        A.DESCRIPCION AS descripcion,
        A.URL_APP AS url
    FROM ADMIN_APP A
    INNER JOIN ADMIN_DETALLE_ROL_APP D ON D.ID_APP = A.ID_APP
    INNER JOIN ADMIN_USUARIO U ON U.ROL_ID = D.ID_ROL
    WHERE U.ID_USUARIO = :userId
      AND A.ESTADO = 'S'
    ORDER BY A.ID_APP
""", nativeQuery = true)
List<AppProjection> findAppsByUserId(@Param("userId") String userId);

}