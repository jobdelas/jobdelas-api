package com.jobdelas.jobdelas.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.jobdelas.jobdelas.dto.PostagemDTO;
import com.jobdelas.jobdelas.dto.RegistroPostagemDTO;
import com.jobdelas.jobdelas.model.Postagens;
import com.jobdelas.jobdelas.model.Usuarios;
import com.jobdelas.jobdelas.service.PostagensService;
import com.jobdelas.jobdelas.service.UsuariosService;

@RestController
@RequestMapping("/postagens")
@CrossOrigin 
public class PostagensController {

    @Autowired
    private PostagensService postagensService;

    @Autowired
    private UsuariosService usuariosService;
    

    @CrossOrigin
    @GetMapping()
    public ResponseEntity<List<PostagemDTO>> listarTodasPostagens() {
        List<Postagens> listaPostagens = postagensService.listarTodasPostagens();
          List<PostagemDTO> listaPostagensDTOs = listaPostagens.stream()
                .map(postagensService::convertToDTO)
                .collect(Collectors.toList());
                return ResponseEntity.ok(listaPostagensDTOs);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<List<PostagemDTO>> listarPostagensPorCategoria(@RequestParam String categoria) {
        List<Postagens> listaPostagensPorCategoria = postagensService.listarPostagensPorCategoria(categoria);
        List<PostagemDTO> listaPostagensPorCategoriaDTOs = listaPostagensPorCategoria.stream()
        .map(postagensService::convertToDTO)
        .collect(Collectors.toList());
        return ResponseEntity.ok(listaPostagensPorCategoriaDTOs);
    }

    @PostMapping()
    public ResponseEntity<Postagens> cadastrarPostagens(@RequestBody RegistroPostagemDTO registroPostagemDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuarios usuario = usuariosService.encontrarUsuarioPorEmail(auth.getName());
    
        Postagens postagens = new Postagens(
                null,
                usuario,
                registroPostagemDTO.conteudo(),
                registroPostagemDTO.categoria(),
                LocalDateTime.now(),
                null
        );
    
        Postagens novaPostagem = postagensService.cadastrarPostagens(postagens);
    
        return ResponseEntity.ok().body(novaPostagem);
    }
    
}
