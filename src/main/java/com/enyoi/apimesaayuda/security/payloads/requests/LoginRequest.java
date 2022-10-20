package com.enyoi.apimesaayuda.security.payloads.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(max = 120)
    private String clave;
}
