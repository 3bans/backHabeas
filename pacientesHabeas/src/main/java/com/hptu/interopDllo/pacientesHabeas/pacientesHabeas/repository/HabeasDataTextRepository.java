package com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hptu.interopDllo.pacientesHabeas.pacientesHabeas.entity.HabeasDataText;

@Repository
public interface HabeasDataTextRepository extends JpaRepository<HabeasDataText, Long> {
}

