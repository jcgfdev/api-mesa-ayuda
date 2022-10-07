package com.enyoi.apimesaayuda.aplicacion.entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "estados_solicitud")
public class EstadosSolicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @Column(length = 30)
    @NotBlank
    private String nombreEstado;



}
