package com.enyoi.apimesaayuda.aplicacion.dtos;

import com.enyoi.apimesaayuda.security.dtos.UsuariosDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudesDTO {
    private Long id;
    private String codigo;
    private TiposSolicitudDTO tipoSolicitudId;
    private DependenciasDTO dependenciasId;
    private UsuariosDTO solicitanteId;
    private String titulo;
    private String descripcion;
    private Date fechaSolicitud;
    private Date fechaFinalizado;
    private EstadosSolicitudDTO estadoId;
    private Long prioridad;
}
