package com.jobdelas.jobdelas.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobdelas.jobdelas.model.Chat;
import com.jobdelas.jobdelas.repository.ChatRepository;
import com.jobdelas.jobdelas.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public List<Chat> pegarTodasMensagens() {
        return chatRepository.findAll();
    }

    @Override
    public void salvarMensagem(Chat chat) {
        chatRepository.save(chat);
    }
}
