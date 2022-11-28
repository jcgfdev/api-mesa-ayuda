package com.enyoi.apimesaayuda.aplicacion.payloads.requests;


import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;


@Getter
@Setter
public class SeguimientosRequest {
    @NonNull
    @NotBlank
    private long solicitudesId;

    @NonNull
    private String titulo;

    @NonNull
    private Date fechaRealizado;

    @NonNull
    private String descripcion;

    @NonNull
    private long responsableId;
    @NonNull
    @NotBlank
    @Email
    private String usuario;

}
