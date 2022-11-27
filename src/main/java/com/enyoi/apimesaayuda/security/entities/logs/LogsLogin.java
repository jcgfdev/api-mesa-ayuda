package com.enyoi.apimesaayuda.security.entities.logs;
import com.enyoi.apimesaayuda.base.enums.Acciones;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "logs_login")
public class LogsLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",  nullable = false)
    private UUID id;
    @NonNull
    private Acciones acciones;
    @NonNull
    private String usuario;
    @NonNull
    private Date fechalog;
}
