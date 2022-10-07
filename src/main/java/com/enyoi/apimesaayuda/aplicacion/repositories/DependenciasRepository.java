package com.enyoi.apimesaayuda.aplicacion.repositories;

import com.enyoi.apimesaayuda.aplicacion.entities.Dependencias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DependenciasRepository extends JpaRepository<Dependencias, Long> {
    @Override
    Optional<Dependencias> findById(Long id);

    Optional<Dependencias> findByNombreDependencia(String nombreDependencia);
}
