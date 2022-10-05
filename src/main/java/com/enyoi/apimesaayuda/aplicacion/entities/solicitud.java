package com.enyoi.apimesaayuda.aplicacion.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "solicitud")

public class solicitud {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @NotBlank
        @Size(max = 100)
        @NonNull
        private String codigo;
        @NotBlank
        @Size(max = 12)
        @NonNull
        private Long tipo_solicitud;
        @NotBlank
        @Size(max = 12)
        @NonNull
        private Long dependencia_id;
        @NotBlank
        @Size(max = 100)
        @NonNull
        private String titulo;
        @NotBlank
        @Size(max = 100)
        @NonNull
        private String descripcion;
        @DateTimeFormat
        @NotNull
        private Date fecha_solicitud;
        @DateTimeFormat
        @NotNull
        private Date fecha_finalizado;
        @NotBlank
        @Size(max = 12)
        @NonNull
        private Long estado_id;




}
