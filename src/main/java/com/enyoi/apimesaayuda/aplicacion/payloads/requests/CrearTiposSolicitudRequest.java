package com.enyoi.apimesaayuda.aplicacion.payloads.requests;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class CrearTiposSolicitudRequest {
    @NonNull
    @NotBlank
    private String tipoSolicitud;
    @NonNull
    @NotBlank
    @Email
    private String usuario;
}
