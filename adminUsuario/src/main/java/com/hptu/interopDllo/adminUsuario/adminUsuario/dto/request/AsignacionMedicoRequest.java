package com.hptu.interopDllo.adminUsuario.adminUsuario.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class AsignacionMedicoRequest {

    private String idUsuario;
    private List<Long> idMedicos;
}