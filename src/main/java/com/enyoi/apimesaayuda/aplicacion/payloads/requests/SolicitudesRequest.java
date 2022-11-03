package com.enyoi.apimesaayuda.aplicacion.payloads.requests;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
public class SolicitudesRequest {
    @NonNull
    @NotBlank
    private String codigo;

    @NonNull
    private Long tipoSolicitudId;

    @NonNull
    private Long dependenciasId;

    @NonNull
    private Long solicitanteId;

    @NonNull
    @NotBlank
    private String titulo;

    @Lob
    @NonNull
    @NotBlank
    private String descripcion;

    @NonNull
    private Date fechaSolicitud;

    private Date fechaFinalizado;

    @NonNull
    private Long estadoId;
}
