package com.jobdelas.jobdelas.dto;

import java.time.LocalDateTime;

public record PostagemDTO(Long id, String nome, String conteudo, LocalDateTime data) {

}
