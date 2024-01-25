package com.jobdelas.jobdelas.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.jobdelas.jobdelas.model.Usuarios;

public interface UsuariosService {
    Usuarios encontrarUsuarioPorEmail(String email);

    Optional<Usuarios> encontrarUsuarioPorId(Long id);

    Usuarios cadastrarUsuario(Usuarios novoUsuario);
}
