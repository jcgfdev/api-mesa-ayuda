package com.enyoi.apimesaayuda.aplicacion.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DependenciasDTO {
    private Long id;
    private String nombreDependencia;
}
