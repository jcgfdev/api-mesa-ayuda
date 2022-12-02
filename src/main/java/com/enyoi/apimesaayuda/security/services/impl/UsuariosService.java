package com.enyoi.apimesaayuda.security.services.impl;

import com.enyoi.apimesaayuda.base.enums.Acciones;
import com.enyoi.apimesaayuda.base.exceptions.*;
import com.enyoi.apimesaayuda.security.configs.JwtUtils;
import com.enyoi.apimesaayuda.security.dtos.UsuariosDTO;
import com.enyoi.apimesaayuda.security.entities.ConfirmationToken;
import com.enyoi.apimesaayuda.security.entities.Roles;
import com.enyoi.apimesaayuda.security.entities.Usuarios;
import com.enyoi.apimesaayuda.security.entities.logs.LogsLogin;
import com.enyoi.apimesaayuda.security.enums.Role;
import com.enyoi.apimesaayuda.security.models.UserDetailsModel;
import com.enyoi.apimesaayuda.security.payloads.requests.LoginRequest;
import com.enyoi.apimesaayuda.security.payloads.requests.UsuariosRequest;
import com.enyoi.apimesaayuda.security.payloads.responses.UsuarioLoginResponse;
import com.enyoi.apimesaayuda.security.repositories.RolesRepository;
import com.enyoi.apimesaayuda.security.repositories.UsuariosRepository;
import com.enyoi.apimesaayuda.security.repositories.logs.LogsLoginRepository;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuariosService implements IUsuariosService {
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    private final UsuariosRepository userRepository;

    private final LogsLoginRepository logsLoginRepository;

    private final RolesRepository roleRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;
    private static final String CLAVESUPERADMI = "123456";

    private static final String ROLEEXCEPTION = "Error: Role is not found.";
    private static final String USUARIONOEXISTE= "El usuarion no exciste";
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
        Usuarios usuarios = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NotDataFound("Usuario no encontrado"));
        LogsLogin logsLogin = LogsLogin.builder()
                .acciones(Acciones.LOGEAR)
                .usuario(loginRequest.getEmail())
                .fechalog(new Date()).build();
        logsLoginRepository.save(logsLogin);
        return UsuarioLoginResponse.builder()
                .id(usuarios.getId())
                .token(jwt)
                .email(userDetailsModel.getUsername())
                .roles(roles).build();

    }

    @Override
    public UsuariosDTO crearUsuario(UsuariosRequest usuariosRequest) {
        if (Boolean.TRUE.equals(userRepository.existsByEmail(usuariosRequest.getEmail()))) {
            throw new AlreadyExists("Error: usuario ya se encuentra registrado con dicho correo");
        } else {
            if (usuariosRequest.getClaveSuperAdmi() != null && usuariosRequest.getClaveSuperAdmi().equals(CLAVESUPERADMI)) {
                Usuarios usuarios = new Usuarios();
                usuarios.setNombres(usuariosRequest.getNombres());
                usuarios.setApellidos(usuariosRequest.getApellidos());
                usuarios.setEmail(usuariosRequest.getEmail());
                usuarios.setClave(encoder.encode(usuariosRequest.getClave()));
                Set<String> strRoles = usuariosRequest.getRoles();
                Set<Roles> roles = new HashSet<>();
                if (strRoles == null) {
                    Roles userRole = roleRepository.findByName(Role.ROLE_USUARIO)
                            .orElseThrow(() -> new RuntimeException(ROLEEXCEPTION));
                    roles.add(userRole);
                } else {
                    strRoles.forEach(role -> {
                        switch (role) {
                            case "admin":
                                Roles adminRol = roleRepository.findByName(Role.ROLE_ADMIN)
                                        .orElseThrow(() -> new RuntimeException(ROLEEXCEPTION));
                                roles.add(adminRol);
                                break;
                            case "tec":
                                Roles tecnicoRol = roleRepository.findByName(Role.ROLE_TECNICO)
                                        .orElseThrow(() -> new RuntimeException(ROLEEXCEPTION));
                                roles.add(tecnicoRol);
                                break;
                            default:
                                Roles usuarioRol = roleRepository.findByName(Role.ROLE_USUARIO)
                                        .orElseThrow(() -> new RuntimeException(ROLEEXCEPTION));
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
            }else {
                throw new SinPermiso("Usuario sin permiso para crear");
            }
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

    @Override
    public String activarUsuarioId(Long userId) {
        Usuarios usuarios = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(USUARIONOEXISTE));
        if (Boolean.FALSE.equals(usuarios.getActivado())) {
            String newToken = UUID.randomUUID().toString();
            ConfirmationToken confirmationToken = new ConfirmationToken();
            confirmationToken.setToken(newToken);
            confirmationToken.setCreatedAt(LocalDateTime.now());
            confirmationToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));

            confirmationToken.setUser(usuarios);
            confirmationTokenService.saveConfirmationToken(confirmationToken);
            String link = "http://localhost:8080/api-mesa-ayuda/auth/confirmToken?token=" + newToken;
            emailService.enviar(usuarios.getEmail(), buildEmailService.buildEmail(usuarios.getNombres(), link));
            return "Email enviado para activar nuevamente al usuario: ";
        }else{
            throw new EmailConfirmado("");
        }

    }

    public UsuariosDTO cambiarClave(Long userId, String newClave, String reNewClave){
        Usuarios usuarios = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(USUARIONOEXISTE));
        if (Objects.equals(newClave, reNewClave)) {
            usuarios.setClave(encoder.encode(newClave));
            return modelMapper.map(userRepository.save(usuarios), UsuariosDTO.class);
             }else{
            throw new NotDataFound("--> La Clave no Existe");
        }

    }

    @Override
    public String recuperarClaveEmail(String email, String url) {
        Usuarios usuarios = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException(USUARIONOEXISTE));
        emailService.enviar(usuarios.getEmail(), buildEmailService.recuperarClaveEmail(usuarios.getNombres(), url));
        return "Email enviado para Recuperar contraseña: ";
    }


}



