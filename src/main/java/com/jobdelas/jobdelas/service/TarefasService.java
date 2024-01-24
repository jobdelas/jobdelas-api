package com.jobdelas.jobdelas.service;

import java.util.List;

import com.jobdelas.jobdelas.model.Tarefas;

public interface TarefasService {
    List<Tarefas> listarTarefas(Long usuarioId);

    Tarefas criarTarefa(Tarefas tarefas);

    void deletarTarefa(Long id);

    Tarefas atualizarTarefas(Long id, Tarefas tarefaAtualizada);

    Tarefas pegarTarefaPorId(Long id);

}
