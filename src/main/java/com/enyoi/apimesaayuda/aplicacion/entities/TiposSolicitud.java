package com.enyoi.apimesaayuda.aplicacion.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "tipos_solicitud")

public class TiposSolicitud {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @NotBlank
        @Size(max = 100)
        @NonNull
        private String tipoSolicitud;
}
