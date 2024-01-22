package com.jobdelas.jobdelas.service;

import com.jobdelas.jobdelas.model.Curtidas;

public interface CurtidasService {

    int contarCurtidasDaPostagem(Long postagemId);

    Curtidas curtirPostagem(Curtidas curtida);

}
