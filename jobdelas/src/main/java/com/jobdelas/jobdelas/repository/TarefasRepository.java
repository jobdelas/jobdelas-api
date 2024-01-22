package com.jobdelas.jobdelas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobdelas.jobdelas.model.Tarefas;


@Repository
public interface TarefasRepository extends JpaRepository<Tarefas, Long> {

    List<Tarefas> findAllByUsuarioId(Long usuarioId);
}