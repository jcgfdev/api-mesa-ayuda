package com.enyoi.apimesaayuda.aplicacion.repositories.logs;

import com.enyoi.apimesaayuda.aplicacion.entities.logs.LogsDependencias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LogsDependenciasRepository extends JpaRepository<LogsDependencias, UUID> {
}
