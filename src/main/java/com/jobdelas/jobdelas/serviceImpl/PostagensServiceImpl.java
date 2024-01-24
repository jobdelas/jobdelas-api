package com.jobdelas.jobdelas.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobdelas.jobdelas.dto.PostagemDTO;
import com.jobdelas.jobdelas.dto.RegistroPostagemDTO;
import com.jobdelas.jobdelas.model.Postagens;
import com.jobdelas.jobdelas.repository.PostagensRepository;
import com.jobdelas.jobdelas.service.PostagensService;

import jakarta.transaction.Transactional;

@Service
public class PostagensServiceImpl implements PostagensService {

    @Autowired
    private PostagensRepository postagensRepository;

    @Override
    public List<Postagens> listarTodasPostagens() {
        return postagensRepository.findAll();
    }

    @Override
    @Transactional
    public Postagens cadastrarPostagens(Postagens postagens) {
        return postagensRepository.save(postagens);
    }

    @Override
    public void deletarPostagens(Long id) {
        postagensRepository.deleteById(id);
    }

    @Override
    public PostagemDTO convertToDTO(Postagens postagens) {
        return new PostagemDTO(
                postagens.getId(),
                postagens.getUsuarios().getNome(),
                postagens.getConteudo(),
                postagens.getData());
    }

}
