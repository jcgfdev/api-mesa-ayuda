package com.enyoi.apimesaayuda.aplicacion.entities;

import com.enyoi.apimesaayuda.security.entities.Usuarios;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Seguimientos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "solicitudes_id",referencedColumnName ="id",nullable = false)
    @ManyToOne(optional = false)
    private Solicitudes solicitudesId;
    @NotBlank
    @Column(name = "titulo",nullable = false,length = 50)
    private String titulo;
    @Column(name = "fecha_realizado",nullable = false)
    private Date fechaRealizado;
    @NotBlank
    @Column(name = "descripcion",nullable = false,length = 2000)
    private String descripcion;
    @JoinColumn(name = "responsable_id",referencedColumnName ="id",nullable = false)
    @ManyToOne(optional = false)
    private Usuarios responsableId;
}
