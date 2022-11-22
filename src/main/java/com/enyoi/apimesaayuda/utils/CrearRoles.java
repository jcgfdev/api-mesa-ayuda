package com.enyoi.apimesaayuda.utils;

import com.enyoi.apimesaayuda.security.entities.Roles;
import com.enyoi.apimesaayuda.security.enums.Role;
import com.enyoi.apimesaayuda.security.services.IRolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CrearRoles implements CommandLineRunner {
    private final IRolesService rolesService;

    @Override
    public void run(String... args) throws Exception {
        Roles rolAdmin = new Roles(Role.ROLE_ADMIN);
        Roles rolTecnico = new Roles(Role.ROLE_TECNICO);
        Roles rolUsuario = new Roles(Role.ROLE_USUARIO);
        rolesService.crearRol(rolAdmin);
        rolesService.crearRol(rolTecnico);
        rolesService.crearRol(rolUsuario);
    }
}
