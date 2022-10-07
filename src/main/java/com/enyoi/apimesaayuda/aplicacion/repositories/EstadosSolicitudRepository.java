package com.enyoi.apimesaayuda.aplicacion.repositories;



import com.enyoi.apimesaayuda.aplicacion.entities.EstadosSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadosSolicitudRepository extends JpaRepository<EstadosSolicitud, Long> {

    @Override
    Optional<EstadosSolicitud> findById(Long id);

    Optional<EstadosSolicitud> findByNombreEstado(String nombreEstado);

}
