package com.jobdelas.jobdelas.dto;

import com.jobdelas.jobdelas.model.UsuariosRole;

public record RegistroUsuarioDTO(String nome, String email, String senha, UsuariosRole cargo) {

}
