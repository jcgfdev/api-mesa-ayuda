package com.enyoi.apimesaayuda.aplicacion.entities;


import com.enyoi.apimesaayuda.security.entities.Usuarios;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "solicitudes")
public class Solicitudes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 5)
    @Column(name = "codigo", nullable = false, length = 20)
    private String codigo;
    @JoinColumn(name = "tipo_solicitud_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private TiposSolicitud tipoSolicitudId;
    @JoinColumn(name = "dependencias_id",referencedColumnName = "id",nullable = false)
    @ManyToOne(optional = false)
    private Dependencias dependenciasId;
    @JoinColumn(name = "solicitante_id",referencedColumnName = "id",nullable = false)
    @ManyToOne(optional = false)
    private Usuarios solicitanteId;
    @NotBlank
    @Column(name = "titulo", nullable = false, length = 50)
    private String titulo;
    @NotBlank
    @Column(name = "descripcion", nullable = false, length = 2000)
    private String descripcion;
    @Column(name = "fecha_solicitud", nullable = false)
    private Date fechaSolicitud;
    @Column(name = "fecha_finalizado")
    private Date fechaFinalizado;
    @JoinColumn(name = "estado_id",referencedColumnName = "id",nullable = false)
    @ManyToOne(optional = false)
    private EstadosSolicitud estadoId;
    @Column(name = "prioridad", nullable = false, length = 1)
    private Long prioridad;
}
