package com.jobdelas.jobdelas.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.jobdelas.jobdelas.dto.PostagemDTO;
import com.jobdelas.jobdelas.dto.RegistroPostagemDTO;
import com.jobdelas.jobdelas.model.Postagens;
import com.jobdelas.jobdelas.model.Usuarios;
import com.jobdelas.jobdelas.service.PostagensService;
import com.jobdelas.jobdelas.service.UsuariosService;

@RestController
@RequestMapping("/postagem")
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
    @PostMapping
    public ResponseEntity<Postagens> cadastrarPostagens(@RequestBody RegistroPostagemDTO data) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuarios usuario = usuariosService.encontrarUsuarioPorEmail(auth.getName());

        Postagens postagem = new Postagens();
        postagem.setUsuarios(usuario);
        postagem.setConteudo(data.conteudo());
        postagem.setData(LocalDateTime.now());

        Postagens novaPostagem = postagensService.cadastrarPostagens(postagem);

        return ResponseEntity.ok().body(novaPostagem);
    }

    @CrossOrigin
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarPostagem(@PathVariable Long id) {
        try {
            postagensService.deletarPostagens(id);
            return ResponseEntity.ok("Postagem deletada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar a postagem.");
        }
    }

    @CrossOrigin
    @GetMapping("/contarPostagensPorUsuario")
    public ResponseEntity<Integer> contarPostagensPorUsuario() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuarios usuario = usuariosService.encontrarUsuarioPorEmail(auth.getName());

        int numeroPostagens = postagensService.contarPostagensPorUsuario(usuario.getNome());
        return ResponseEntity.ok(numeroPostagens);
    }

}
