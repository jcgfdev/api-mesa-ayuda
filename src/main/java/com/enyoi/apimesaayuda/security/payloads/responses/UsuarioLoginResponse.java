package com.enyoi.apimesaayuda.security.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioLoginResponse {

    private Long id;
    private String token;
    private String email;
    List<String> roles;
}
