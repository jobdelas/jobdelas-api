package com.jobdelas.jobdelas.service;

import java.util.List;

import com.jobdelas.jobdelas.model.Chat;

public interface ChatService {
    List<Chat> pegarTodasMensagens();

    void salvarMensagem(Chat chat);
}
