package com.jobdelas.jobdelas.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthorizationService  extends UserDetailsService{
       UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    
}
