package com.enyoi.apimesaayuda.aplicacion.services;

import com.enyoi.apimesaayuda.aplicacion.dtos.DependenciasDTO;
import com.enyoi.apimesaayuda.aplicacion.entities.Dependencias;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.DependenciasRequests;

import java.util.List;

public interface IDependenciasService {
    List<DependenciasDTO> findAll();

    DependenciasDTO findById(Long id);

    DependenciasDTO findByNombreDependencia(String nombreDependencia);

    DependenciasDTO create(String nombreDependencia);

    DependenciasDTO update(DependenciasRequests dependenciasRequests);

    String delete(Long id);
}
