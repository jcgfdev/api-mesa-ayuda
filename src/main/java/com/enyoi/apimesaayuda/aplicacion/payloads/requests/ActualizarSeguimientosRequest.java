package com.enyoi.apimesaayuda.aplicacion.payloads.requests;

import com.enyoi.apimesaayuda.security.dtos.UsuariosDTO;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
public class ActualizarSeguimientosRequest {
    @NonNull
    private Long seguimientoId;

    @NonNull
    private Long solicitudesId;

    @NonNull
    @NotBlank
    private String titulo;

    @NonNull
    private Date fechaRealizado;

    @NonNull
    @NotBlank
    private String descripcion;

    @NonNull
    private Long responsableId;

}
