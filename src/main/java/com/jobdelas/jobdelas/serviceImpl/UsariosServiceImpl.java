package com.jobdelas.jobdelas.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobdelas.jobdelas.model.Usuarios;
import com.jobdelas.jobdelas.repository.UsuariosRepository;
import com.jobdelas.jobdelas.service.UsuariosService;

@Service
public class UsariosServiceImpl implements UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    public Usuarios encontrarUsuarioPorEmail(String email) {
        return usuariosRepository.findByEmail(email);
    }

    @Override
    public Optional<Usuarios> encontrarUsuarioPorId(Long id) {
        return usuariosRepository.findById(id);

    }

    @Override
    public Usuarios editarUsuario(Usuarios usuario) {
        Usuarios usuarioExistente = usuariosRepository.findById(usuario.getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setFoto(usuario.getFoto());
        usuarioExistente.setStatus(usuario.getStatus());
        usuarioExistente.setNascimento(usuario.getNascimento());
        usuarioExistente.setCep(usuario.getCep());
        usuarioExistente.setSenha(usuario.getSenha());

        return usuariosRepository.save(usuarioExistente);
    }

    @Override
    public Usuarios cadastrarUsuario(Usuarios novoUsuario) {
        return usuariosRepository.save(novoUsuario);
    }

}
