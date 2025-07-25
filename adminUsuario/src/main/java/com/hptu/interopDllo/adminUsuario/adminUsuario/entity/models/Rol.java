package com.hptu.interopDllo.adminUsuario.adminUsuario.entity.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ADMIN_ROL")
public class Rol {
    @Id
    @Column(name = "ID_ROL")
    private String id;

    @Column(name = "NOMBRE_ROL")
    private String sernom;

    @Column(name = "ESTADO")
    private String estado;

    // Getters y Setters
}
