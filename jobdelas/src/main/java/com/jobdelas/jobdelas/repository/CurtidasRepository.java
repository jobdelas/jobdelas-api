package com.jobdelas.jobdelas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobdelas.jobdelas.model.Curtidas;

public interface CurtidasRepository extends JpaRepository<Curtidas, Long> {

    int countByPostagensId(Long postagemId);
}