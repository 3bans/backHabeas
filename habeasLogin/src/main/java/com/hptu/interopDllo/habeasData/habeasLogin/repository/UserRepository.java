package com.hptu.interopDllo.habeasData.habeasLogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hptu.interopDllo.habeasData.habeasLogin.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByIdUsuario(String idUsuario);
}
