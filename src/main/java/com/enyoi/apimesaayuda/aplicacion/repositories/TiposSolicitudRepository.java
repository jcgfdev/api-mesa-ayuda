package com.enyoi.apimesaayuda.aplicacion.repositories;

import com.enyoi.apimesaayuda.aplicacion.entities.TiposSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TiposSolicitudRepository extends JpaRepository<TiposSolicitud, Long> {
    @Override
    Optional<TiposSolicitud> findById(Long id);

    Optional<TiposSolicitud> findByTipoSolicitud(String tipoSolicitud);
}