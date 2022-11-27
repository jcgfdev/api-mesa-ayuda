package com.enyoi.apimesaayuda.aplicacion.entities.logs;

import com.enyoi.apimesaayuda.base.enums.Acciones;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "logs_dependencias")
public class LogsDependencias {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",  nullable = false)
    private UUID id;
    @NonNull
    private Acciones acciones;
    @NonNull
    private String dependencias;
    @NonNull
    private Date fechalog;
    @NonNull
    @NotBlank
    @Email
    private String usuario;
}
