package com.enyoi.apimesaayuda.aplicacion.payloads.requests;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TiposSolicitudRequests {
    @NonNull
    private Long tipoSolicitudId;
    @NotBlank
    private String tipoSolicitud;
}
