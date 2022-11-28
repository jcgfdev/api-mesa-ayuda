package com.enyoi.apimesaayuda.aplicacion.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadosSolicitudDTO {
    private Long id;
    private String nombreEstado;
}

