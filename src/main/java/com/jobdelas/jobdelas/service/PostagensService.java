package com.jobdelas.jobdelas.service;

import java.util.List;

import com.jobdelas.jobdelas.dto.PostagemDTO;
import com.jobdelas.jobdelas.model.Postagens;

public interface PostagensService {
    List<Postagens> listarTodasPostagens();

    Postagens cadastrarPostagens(Postagens postagens);

    void deletarPostagens(Long id);

    PostagemDTO convertToDTO(Postagens postagens);

    int contarPostagensPorUsuario(String nomeUsuario);

}
