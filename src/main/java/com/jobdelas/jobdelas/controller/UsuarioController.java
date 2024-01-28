package com.jobdelas.jobdelas.controller;

import java.io.IOException;

import org.springframework.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobdelas.jobdelas.dto.LoginResponseDTO;
import com.jobdelas.jobdelas.dto.RegistroUsuarioDTO;
import com.jobdelas.jobdelas.dto.UsuarioDTO;
import com.jobdelas.jobdelas.model.Usuarios;
import com.jobdelas.jobdelas.repository.UsuariosRepository;
import com.jobdelas.jobdelas.service.TokenService;
import com.jobdelas.jobdelas.service.UsuariosService;

@RestController
@RequestMapping("auth")
public class UsuarioController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private UsuariosService usuariosService;

    @Value("${imgur.client-id}")
    private String imgurClientId;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @CrossOrigin
    @PostMapping("/logar")
    public ResponseEntity<?> logar(@RequestBody UsuarioDTO data) {
        var usuario = new UsernamePasswordAuthenticationToken(data.email(),
                data.senha());
        try {
            var auth = this.authenticationManager.authenticate(usuario);
            var token = tokenService.gerarToken((Usuarios) auth.getPrincipal());
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos");
        }
    }

    @CrossOrigin
    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody RegistroUsuarioDTO data) {

        Usuarios usuarioExistente = usuariosService.encontrarUsuarioPorEmail(data.email());

        if (usuarioExistente != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já existe");

        }

        String senhaCriptografada = new BCryptPasswordEncoder().encode(data.senha());

        Usuarios novoUsuario = new Usuarios();
        novoUsuario.setNome(data.nome());
        novoUsuario.setEmail(data.email());
        novoUsuario.setSenha(senhaCriptografada);
        novoUsuario.setCargo(data.cargo());

        this.usuariosService.cadastrarUsuario(novoUsuario);

        return ResponseEntity.ok().body(novoUsuario);

    }

    @CrossOrigin
    @PutMapping("/editar")
    public ResponseEntity<?> editarUsuario(@RequestBody Usuarios usuarioAtualizado) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuarios usuarioExistente = usuariosService.encontrarUsuarioPorEmail(auth.getName());

        if (usuarioExistente == null) {
            return ResponseEntity.notFound().build();
        }

        Usuarios usuarioComMesmoEmail = usuariosService.encontrarUsuarioPorEmail(usuarioAtualizado.getEmail());
        if (usuarioComMesmoEmail != null && !usuarioComMesmoEmail.getId().equals(usuarioExistente.getId())) {
            return ResponseEntity.badRequest().body("E-mail já está em uso por outro usuário.");
        }

        try {
            usuarioExistente.setEmail(usuarioAtualizado.getEmail());
            usuarioExistente.setFoto(usuarioExistente.getFoto());
            usuarioExistente.setStatus(usuarioAtualizado.getStatus());
            usuarioExistente.setCep(usuarioAtualizado.getCep());

            if (usuarioAtualizado.getSenha() == null) {
                usuarioExistente.setSenha(usuarioExistente.getSenha());
            } else {
                String senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioAtualizado.getSenha());
                usuarioExistente.setSenha(senhaCriptografada);
            }

            this.usuariosRepository.save(usuarioExistente);

            return ResponseEntity.ok().body(usuarioExistente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @CrossOrigin
    @GetMapping("/detalhes")
    public Usuarios obterDetalhesUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailUsuarioLogado = auth.getName();
        return usuariosService.encontrarUsuarioPorEmail(emailUsuarioLogado);
    }

    @CrossOrigin
    @PatchMapping("/editar-foto")
    public ResponseEntity<String> editarFotoUsuario(@RequestPart("foto") MultipartFile foto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Usuarios usuarioExistente = usuariosService.encontrarUsuarioPorEmail(auth.getName());

        if (usuarioExistente == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            String imgurUploadUrl = "https://api.imgur.com/3/image";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Client-ID " + imgurClientId);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image", new ByteArrayResource(foto.getBytes()) {
                @Override
                public String getFilename() {
                    return "image.jpg";
                }
            });

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = new RestTemplate().postForEntity(imgurUploadUrl, requestEntity,
                    String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                String imgurImageUrl = extractImageUrlFromImgurApiResponse(response.getBody());
                usuarioExistente.setFoto(imgurImageUrl);
                usuariosRepository.save(usuarioExistente);
                return ResponseEntity.ok()
                        .body("Foto do usuário atualizada com sucesso. URL da imagem: " + imgurImageUrl);
            } else {
                return ResponseEntity.status(response.getStatusCode())
                        .body("Erro ao enviar a foto para Imgur. Resposta: " + response.getBody());
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar a foto: " + e.getMessage());
        }
    }

    private String extractImageUrlFromImgurApiResponse(String apiResponse) {
        try {
            JsonNode jsonNode = objectMapper.readTree(apiResponse);

            if (jsonNode.has("data") && jsonNode.get("data").has("link")) {
                return jsonNode.get("data").get("link").asText();
            } else {
                throw new RuntimeException("Resposta da API do Imgur não contém a URL da imagem esperada.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao analisar a resposta da API do Imgur.", e);
        }
    }
}
