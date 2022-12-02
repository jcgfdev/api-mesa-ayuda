package com.enyoi.apimesaayuda.aplicacion.repositories.logs;

import com.enyoi.apimesaayuda.aplicacion.entities.logs.LogsSolicitudes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface LogsSolicitudesRepository extends JpaRepository<LogsSolicitudes, UUID> {
}
