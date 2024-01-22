package com.jobdelas.jobdelas.dto;

import java.time.LocalDateTime;

import com.jobdelas.jobdelas.model.Usuarios;

public record RegistroPostagemDTO(Usuarios usuarios, String conteudo,  String categoria, LocalDateTime data_postagem) {
    
}
