package com.jobdelas.jobdelas.service;

import java.util.ArrayList;
import java.util.List;

import com.jobdelas.jobdelas.model.Amizade;

public class AmizadeService {


   private List<Amizade> amizades; // Simulação de armazenamento de dados

    public AmizadeService() {
        this.amizades = new ArrayList<>();
    }

    public void criarAmizade(Amizade amizade) {
        // Lógica para criar uma nova amizade (solicitação pendente)
        amizades.add(amizade);
    }

    public void atualizarAmizade(Amizade amizade) {
        // Lógica para atualizar o status da amizade (aceitar solicitação)
        // Implementação depende do armazenamento de dados (pode ser um banco de dados, por exemplo)
        for (int i = 0; i < amizades.size(); i++) {
            if (amizades.get(i).getId() == amizade.getId()) {
                amizades.set(i, amizade);
                break;
            }
        }
    }

    public Amizade getAmizadeById(int idAmizade) {
        // Lógica para obter uma amizade pelo ID
        for (Amizade amizade : amizades) {
            if (amizade.getId() == idAmizade) {
                return amizade;
            }
        }
        return null; // Retorna null se a amizade não for encontrada
    }

    public List<Amizade> getAmizadesByIdUsuario(int idUsuario) {
        // Lógica para obter todas as amizades de um usuário
        List<Amizade> amizadesUsuario = new ArrayList<>();
        for (Amizade amizade : amizades) {
            if (amizade.getId_usuario1() == idUsuario || amizade.getId_usuario2() == idUsuario) {
                amizadesUsuario.add(amizade);
            }
        }
        return amizadesUsuario;
    }
}


