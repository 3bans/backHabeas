package com.hptu.interopDllo.menuHabeas.menuHabeas.service.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hptu.interopDllo.menuHabeas.menuHabeas.dto.response.AppResponse;
import com.hptu.interopDllo.menuHabeas.menuHabeas.repository.AppRepository;
import com.hptu.interopDllo.menuHabeas.menuHabeas.service.AppService;

@Service
public class AppServiceImpl implements AppService {

    private final AppRepository appRepository;

    public AppServiceImpl(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @Override
    public List<AppResponse> obtenerAplicacionesPorUsuario(String userId) {
        return appRepository.findAppsByUserId(userId)
                .stream()
                .map(p -> new AppResponse(
                        p.getId(),
                        p.getNombre(),
                        p.getDescripcion(),
                        p.getUrl()
                ))
                .toList();
    }
}
