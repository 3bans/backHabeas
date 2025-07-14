package com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ADMIN_PUNTO_SERVICIO")
public class PuntoServicio {
    @Id
    @Column(name = "ID_PUNTO")
    private String id;

    @Column(name = "NOMBRE_PUNTO")
    private String sernom;

    @Column(name = "ESTADO")
    private String estado;

    // Getters y Setters
}
