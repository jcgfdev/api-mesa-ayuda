package com.enyoi.apimesaayuda.security.services;

import com.enyoi.apimesaayuda.security.dtos.UsuariosDTO;
import com.enyoi.apimesaayuda.security.payloads.requests.LoginRequest;
import com.enyoi.apimesaayuda.security.payloads.requests.UsuariosRequest;
import com.enyoi.apimesaayuda.security.payloads.responses.UsuarioLoginResponse;

public interface IUsuariosService {
    UsuarioLoginResponse loginUsuario(LoginRequest loginRequest);

    UsuariosDTO crearUsuario(UsuariosRequest usuariosRequest);

    String confirmarToken(String token);

    void activarUsuarioEmail(String email);
}