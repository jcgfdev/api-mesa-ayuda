package com.enyoi.apimesaayuda.security.services.impl;

import com.enyoi.apimesaayuda.security.entities.Usuarios;
import com.enyoi.apimesaayuda.security.models.UserDetailsModel;
import com.enyoi.apimesaayuda.security.repositories.UsuariosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsuariosRepository usuariosRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuarios usuarios = usuariosRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No existe un usuario con email: " + email));
        return UserDetailsModel.build(usuarios);
    }
}
