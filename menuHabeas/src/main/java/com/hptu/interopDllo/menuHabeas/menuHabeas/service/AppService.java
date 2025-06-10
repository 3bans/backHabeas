package com.hptu.interopDllo.menuHabeas.menuHabeas.service;

import java.util.List;

import com.hptu.interopDllo.menuHabeas.menuHabeas.dto.response.AppResponse;

public interface AppService {
    List<AppResponse> obtenerAplicacionesPorUsuario(String userId);
}
