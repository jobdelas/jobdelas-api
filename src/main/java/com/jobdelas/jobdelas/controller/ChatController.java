package com.jobdelas.jobdelas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobdelas.jobdelas.model.Chat;
import com.jobdelas.jobdelas.model.Usuarios;
import com.jobdelas.jobdelas.service.ChatService;
import com.jobdelas.jobdelas.service.UsuariosService;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UsuariosService usuariosService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<Chat>> getChatMessages() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Usuarios usuario = usuariosService.encontrarUsuarioPorEmail(auth.getName());

        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<Chat> chatMessages = chatService.pegarTodasMensagens();
        return new ResponseEntity<>(chatMessages, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/enviar")
    public ResponseEntity<?> enviarMensagem(
            @RequestBody Chat chat) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Usuarios usuario = usuariosService.encontrarUsuarioPorEmail(auth.getName());

        if (usuario == null) {
            return new ResponseEntity<>("Usuário não autenticado", HttpStatus.UNAUTHORIZED);
        }

        if (chat.getMensagem() != "") {

            Chat chatEnviar = new Chat();
            chatEnviar.setUsuario(usuario);
            chatEnviar.setMensagem(chat.getMensagem());

            chatService.salvarMensagem(chatEnviar);
            return ResponseEntity.ok(chatEnviar);
        }

        return new ResponseEntity<>("A mensagem não pode estar vazia", HttpStatus.BAD_REQUEST);
    }
}
