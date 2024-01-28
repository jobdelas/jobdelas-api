package com.jobdelas.jobdelas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobdelas.jobdelas.model.Tarefas;
import com.jobdelas.jobdelas.model.Usuarios;
import com.jobdelas.jobdelas.service.TarefasService;
import com.jobdelas.jobdelas.service.UsuariosService;

@RestController
@RequestMapping("/tarefas")
public class TarefasController {

    @Autowired
    private TarefasService tarefasService;

    @Autowired
    private UsuariosService usuariosService;

    @CrossOrigin
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Tarefas>> listarTarefasPorUsuario(@PathVariable Long usuarioId) {
        List<Tarefas> tarefas = tarefasService.listarTarefas(usuarioId);
        return ResponseEntity.ok(tarefas);
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Tarefas> criarTarefa(@RequestBody Tarefas tarefas) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuarios usuario = usuariosService.encontrarUsuarioPorEmail(auth.getName());
        tarefas.setUsuario(usuario);

        Tarefas novaTarefa = tarefasService.criarTarefa(tarefas);
        return ResponseEntity.ok(novaTarefa);
    }

    @CrossOrigin
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarTarefa(@PathVariable Long id) {
        tarefasService.deletarTarefa(id);
        return ResponseEntity.ok("Tarefa deletada com sucesso!");
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Tarefas> pegarTarefaPorId(@PathVariable Long id) {
        try {
            Tarefas tarefa = tarefasService.pegarTarefaPorId(id);
            return ResponseEntity.ok(tarefa);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
