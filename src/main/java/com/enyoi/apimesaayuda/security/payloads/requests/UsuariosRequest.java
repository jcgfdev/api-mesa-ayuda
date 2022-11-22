package com.enyoi.apimesaayuda.security.payloads.requests;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class UsuariosRequest {
    @NotBlank
    @Size(max = 120)
    @NonNull
    private String nombres;
    @NotBlank
    @Size(max = 120)
    @NonNull
    private String apellidos;
    @NotBlank
    @Size(max = 50)
    @NonNull
    @Email
    private String email;
    private Set<String> roles;
    @NotBlank
    @Size(max = 120)
    @NonNull
    private String clave;
}
