package com.hptu.interopDllo.habeasData.habeasLogin.service;



import com.hptu.interopDllo.habeasData.habeasLogin.dto.response.ResponseDto;
import com.hptu.interopDllo.habeasData.habeasLogin.entity.UserEntity;

import java.util.Optional;

import org.springframework.stereotype.Service;


@Service

public interface AuthService {
    ResponseDto callExternalService(String url, Object requestBody);
        Optional<UserEntity> findUserById(String idUsuario);

}


