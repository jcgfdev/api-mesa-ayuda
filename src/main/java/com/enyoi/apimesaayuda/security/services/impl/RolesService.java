package com.enyoi.apimesaayuda.security.services.impl;

import com.enyoi.apimesaayuda.security.entities.Roles;
import com.enyoi.apimesaayuda.security.repositories.RolesRepository;
import com.enyoi.apimesaayuda.security.services.IRolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolesService implements IRolesService {
    private final RolesRepository rolesRepository;

    @Override
    public Roles crearRol(Roles role) {
        Optional<Roles> roles = rolesRepository.findByName(role.getName());
        if (roles.isEmpty()) {
            return rolesRepository.save(role);
        } else {
            return roles.get();
        }
    }
}
