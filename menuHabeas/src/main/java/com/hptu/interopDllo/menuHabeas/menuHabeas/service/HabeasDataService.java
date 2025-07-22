package com.hptu.interopDllo.menuHabeas.menuHabeas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hptu.interopDllo.menuHabeas.menuHabeas.dto.response.HabeasDataResponse;
import com.hptu.interopDllo.menuHabeas.menuHabeas.repository.HabeasDataRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HabeasDataService {

    private final HabeasDataRepository repository;

    public List<HabeasDataResponse> obtenerFiltrados(String identificacion, String idMotivo,
                                                     String fechaInicio, String fechaFin, String aprobacion) {
        return repository.consultarConFiltros(identificacion, idMotivo, fechaInicio, fechaFin, aprobacion);
    }

    public List<HabeasDataResponse> obtenerTodos() {
        return repository.consultarTodos();
    }
}