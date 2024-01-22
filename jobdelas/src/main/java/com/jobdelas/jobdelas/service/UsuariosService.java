package com.jobdelas.jobdelas.service;

import java.util.Optional;

import com.jobdelas.jobdelas.model.Usuarios;

public interface UsuariosService {
    Usuarios encontrarUsuarioPorEmail(String email);

    Optional<Usuarios> encontrarUsuarioPorId(Long id);

    Usuarios editarUsuario(Usuarios usuario);

    Usuarios cadastrarUsuario(Usuarios novoUsuario);
}
