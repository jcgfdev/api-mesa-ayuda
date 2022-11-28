package com.enyoi.apimesaayuda.aplicacion.repositories.logs;

import com.enyoi.apimesaayuda.aplicacion.entities.logs.LogsEstadosSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LogsEstadosSolicitudRepository extends JpaRepository<LogsEstadosSolicitud, UUID> {
}
