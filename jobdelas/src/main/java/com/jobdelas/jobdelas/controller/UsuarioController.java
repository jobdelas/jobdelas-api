package com.jobdelas.jobdelas.controller;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha invalidos");
        }
    }

    @CrossOrigin
    @PostMapping("/cadastrar")
    public ResponseEntity<Usuarios> cadastrarUsuario(@RequestBody RegistroUsuarioDTO data) {

        Usuarios usuarioExistente = usuariosService.encontrarUsuarioPorEmail(data.email());

        if(usuarioExistente != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
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
}
