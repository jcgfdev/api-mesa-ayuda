package com.enyoi.apimesaayuda.aplicacion.repositories;

import com.enyoi.apimesaayuda.aplicacion.entities.Seguimientos;
import com.enyoi.apimesaayuda.aplicacion.entities.Solicitudes;
import com.enyoi.apimesaayuda.security.entities.Usuarios;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface SeguimientosRepository extends JpaRepository<Seguimientos, Long> {

    @Override
    Optional<Seguimientos> findById(Long id);

    Page<Seguimientos> findBySolicitudesId(Solicitudes solicitudesId, Pageable pageable);

    Page<Seguimientos> findByFechaRealizadoBetween(Date fechaInicio, Date fechaFin, Pageable pageable);

    Page<Seguimientos> findByResponsableId(Usuarios responsableId, Pageable pageable);

    Optional<Seguimientos> findBySolicitudesId(long solicitudesId);
}
