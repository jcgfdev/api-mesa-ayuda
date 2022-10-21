package com.enyoi.apimesaayuda.security.services.impl;

import com.enyoi.apimesaayuda.base.exceptions.EmailException;
import com.enyoi.apimesaayuda.base.exceptions.AlreadyExists;
import com.enyoi.apimesaayuda.base.exceptions.NotDataFound;
import com.enyoi.apimesaayuda.security.configs.JwtUtils;
import com.enyoi.apimesaayuda.security.dtos.UsuariosDTO;
import com.enyoi.apimesaayuda.security.entities.ConfirmationToken;
import com.enyoi.apimesaayuda.security.entities.Roles;
import com.enyoi.apimesaayuda.security.entities.Usuarios;
import com.enyoi.apimesaayuda.security.enums.Role;
import com.enyoi.apimesaayuda.security.models.UserDetailsModel;
import com.enyoi.apimesaayuda.security.payloads.requests.LoginRequest;
import com.enyoi.apimesaayuda.security.payloads.requests.UsuariosRequest;
import com.enyoi.apimesaayuda.security.payloads.responses.UsuarioLoginResponse;
import com.enyoi.apimesaayuda.security.repositories.RolesRepository;
import com.enyoi.apimesaayuda.security.repositories.UsuariosRepository;
import com.enyoi.apimesaayuda.security.services.IBuildEmailService;
import com.enyoi.apimesaayuda.security.services.IConfirmationTokenService;
import com.enyoi.apimesaayuda.security.services.IEmailService;
import com.enyoi.apimesaayuda.security.services.IUsuariosService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuariosService implements IUsuariosService {
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    private final UsuariosRepository userRepository;

    private final RolesRepository roleRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    private static final String ROLEEXCEPTION = "Error: Role is not found.";
    private final IConfirmationTokenService confirmationTokenService;
    private final IEmailService emailService;

    private final IBuildEmailService buildEmailService;

    @Override
    public UsuarioLoginResponse loginUsuario(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getClave()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generarJwtToken(authentication);
        UserDetailsModel userDetailsModel = (UserDetailsModel) authentication.getPrincipal();
        List<String> roles = userDetailsModel.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        UsuarioLoginResponse usuarioLoginResponse = UsuarioLoginResponse.builder()
                .token(jwt)
                .email(userDetailsModel.getUsername())
                .roles(roles).build();
        return usuarioLoginResponse;
    }

    @Override
    public UsuariosDTO crearUsuario(UsuariosRequest usuariosRequest) {
        if (Boolean.TRUE.equals(userRepository.existsByEmail(usuariosRequest.getEmail()))) {
            throw new AlreadyExists("Error: usuario ya se encuentra registrado con dicho correo");
        } else {
            Usuarios usuarios = new Usuarios();
            usuarios.setNombres(usuariosRequest.getNombres());
            usuarios.setApellidos(usuariosRequest.getApellidos());
            usuarios.setEmail(usuariosRequest.getEmail());
            usuarios.setClave(encoder.encode(usuariosRequest.getClave()));
            Set<String> strRoles = usuariosRequest.getRoles();
            Set<Roles> roles = new HashSet<>();
            if (strRoles == null) {
                Roles userRole = roleRepository.findByName(Role.ROLE_USUARIO)
                        .orElseThrow(() -> new RuntimeException("Error: rol no existe"));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "admin":
                            Roles adminRol = roleRepository.findByName(Role.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: rol no existe"));
                            roles.add(adminRol);
                            break;
                        case "tec":
                            Roles tecnicoRol = roleRepository.findByName(Role.ROLE_TECNICO)
                                    .orElseThrow(() -> new RuntimeException("Error: rol no existe"));
                            roles.add(tecnicoRol);
                            break;
                        default:
                            Roles usuarioRol = roleRepository.findByName(Role.ROLE_USUARIO)
                                    .orElseThrow(() -> new RuntimeException("Error: rol no existe"));
                            roles.add(usuarioRol);
                    }
                });
            }
            usuarios.setRoles(roles);
            UsuariosDTO usuariosDTO = modelMapper.map(userRepository.save(usuarios), UsuariosDTO.class);
            String token = UUID.randomUUID().toString();
            ConfirmationToken confirmationToken = new ConfirmationToken();
            confirmationToken.setToken(token);
            confirmationToken.setCreatedAt(LocalDateTime.now());
            confirmationToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));
            confirmationToken.setUser(usuarios);
            confirmationTokenService.saveConfirmationToken(confirmationToken);
            String link = "http://localhost:8080/api-mesa-ayuda/auth/confirmToken?token=" + token;
            String nombre = usuariosDTO.getNombres() + " " + usuariosDTO.getApellidos();
            emailService.enviar(usuariosDTO.getEmail(), buildEmailService.buildEmail(nombre, link));
            return usuariosDTO;
        }
    }

    @Override
    public String confirmarToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.findConfirmationTokenByToken(token)
                .orElseThrow(() -> new NotDataFound("token no existe"));
        if (confirmationToken.getConfirmedAt() != null) {
            throw new EmailException("correo ya confirmado");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new EmailException("token expiro");
        }
        int confirmed = confirmationTokenService.setTokenConfirmedAt(token);
        activarUsuarioEmail(confirmationToken.getUser().getEmail());
        if (confirmed == 1) {
            return confirmationToken.getUser().getEmail() + " ha sido confirmado";
        } else {
            return confirmationToken.getUser().getEmail() + " no ha sido confirmado";
        }
    }

    @Override
    public void activarUsuarioEmail(String email) {
        userRepository.activarUsuario(email);
    }
}