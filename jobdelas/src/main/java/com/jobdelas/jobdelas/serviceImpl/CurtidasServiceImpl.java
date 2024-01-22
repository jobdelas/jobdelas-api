package com.jobdelas.jobdelas.serviceImpl;
import com.jobdelas.jobdelas.model.Curtidas;
import com.jobdelas.jobdelas.repository.CurtidasRepository;
import com.jobdelas.jobdelas.service.CurtidasService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurtidasServiceImpl implements CurtidasService {

    @Autowired
    private CurtidasRepository curtidasRepository;

    @Override
    public Curtidas curtirPostagem(Curtidas curtida) {
        return curtidasRepository.save(curtida);
    }

    @Override
    public int contarCurtidasDaPostagem(Long postagemId) {
        return curtidasRepository.countByPostagensId(postagemId);
    }

}