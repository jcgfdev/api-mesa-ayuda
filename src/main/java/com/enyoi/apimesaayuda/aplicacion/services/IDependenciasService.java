package com.enyoi.apimesaayuda.aplicacion.services;

import com.enyoi.apimesaayuda.aplicacion.dtos.DependenciasDTO;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.CrearDependenciasRequest;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.DependenciasRequests;

import java.util.List;

public interface IDependenciasService {
    List<DependenciasDTO> findAll(String user);

    DependenciasDTO findById(Long id, String user);

    DependenciasDTO findByNombreDependencia(String nombreDependencia, String user);

    DependenciasDTO create(CrearDependenciasRequest crearDependenciasRequest);

    DependenciasDTO update(DependenciasRequests dependenciasRequests);

    String delete(Long id, String usuarios);
}
