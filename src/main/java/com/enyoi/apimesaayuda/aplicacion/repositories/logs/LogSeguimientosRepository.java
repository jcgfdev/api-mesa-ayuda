package com.enyoi.apimesaayuda.aplicacion.repositories.logs;


import com.enyoi.apimesaayuda.aplicacion.entities.logs.LogsSeguimientos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LogSeguimientosRepository extends JpaRepository<LogsSeguimientos, UUID> {
}
