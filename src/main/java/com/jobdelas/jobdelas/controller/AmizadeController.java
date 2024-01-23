package com.jobdelas.jobdelas.controller;

import java.util.List;

import com.jobdelas.jobdelas.model.Amizade;
import com.jobdelas.jobdelas.service.AmizadeService;

public class AmizadeController {
    private AmizadeService amizadeService;

    public AmizadeController(AmizadeService amizadeService) {
        this.amizadeService = amizadeService;
    }


    public void criarAmizade(int id_usuario1, int id_usuario2) {
        // Lógica para criar uma nova solicitação de amizade
        Amizade amizade = new Amizade(0, id_usuario1, id_usuario2, false); // Status padrão é pendente
        amizadeService.criarAmizade(amizade);
    }

    public void aceitarAmizade(int idAmizade) {
        // Lógica para aceitar uma solicitação de amizade
        Amizade amizade = amizadeService.getAmizadeById(idAmizade);
        if (amizade != null && !amizade.isStatus()) {
            amizade.setStatus(true); // Marcando como aceito
            amizadeService.atualizarAmizade(amizade);
        }
    }

    public List<Amizade> listarAmizades(int idUsuario) {
        // Lógica para listar todas as amizades de um usuário
        return amizadeService.getAmizadesByIdUsuario(idUsuario);
    }
    // Métodos do controlador para interagir com o serviço
    // Exemplo: criarAmizade, aceitarAmizade, listarAmizades, etc.


}