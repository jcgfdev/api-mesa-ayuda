package com.enyoi.apimesaayuda.aplicacion.dtos;

import com.enyoi.apimesaayuda.security.dtos.UsuariosDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeguimientosDTO {
    private Long id;
    private SolicitudesDTO solicitudesId;
    private String titulo;
    private Date fechaRealizado;
    private String descripcion;
    private UsuariosDTO responsableId;
}
