package com.enyoi.apimesaayuda.aplicacion.repositories;

import com.enyoi.apimesaayuda.aplicacion.entities.Dependencias;
import com.enyoi.apimesaayuda.aplicacion.entities.EstadosSolicitud;
import com.enyoi.apimesaayuda.aplicacion.entities.Solicitudes;
import com.enyoi.apimesaayuda.aplicacion.entities.TiposSolicitud;
import com.enyoi.apimesaayuda.security.entities.Usuarios;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface SolicitudesRepository extends JpaRepository<Solicitudes, Long> {
    @Override
    Optional<Solicitudes> findById(Long id);

    Optional<Solicitudes> findByCodigo(String codigo);

    Page<Solicitudes> findByTipoSolicitudId(TiposSolicitud tiposSolicitudId, Pageable pageable);

    Page<Solicitudes> findByDependenciasId(Dependencias dependenciasId, Pageable pageable);

    Page<Solicitudes> findBySolicitanteId(Usuarios solicitanteId, Pageable pageable);

    Page<Solicitudes> findByFechaSolicitudBetween(Date fechaInicio, Date fechaFin,Pageable pageable);

    Page<Solicitudes> findByFechaFinalizadoBetween(Date fechaInicio, Date fechaFin, Pageable pageable);

    Page<Solicitudes> findByEstadoId(EstadosSolicitud estadoId, Pageable pageable);

    Optional<Solicitudes> findByPrioridad(Long prioridad);



}
