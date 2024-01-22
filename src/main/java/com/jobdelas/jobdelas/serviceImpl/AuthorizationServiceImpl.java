package com.jobdelas.jobdelas.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jobdelas.jobdelas.repository.UsuariosRepository;
import com.jobdelas.jobdelas.service.AuthorizationService;

@Service
public class AuthorizationServiceImpl implements AuthorizationService{

     @Autowired
    private UsuariosRepository usuariosRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuariosRepository.findByEmail(username);
    }
    
}
