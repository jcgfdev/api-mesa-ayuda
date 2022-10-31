package com.enyoi.apimesaayuda.aplicacion.services;

import com.enyoi.apimesaayuda.aplicacion.dtos.DependenciasDTO;
import com.enyoi.apimesaayuda.aplicacion.dtos.EstadosSolicitudDTO;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.ActualizarEstadosSolicitudRequests;

import java.util.List;

public interface IEstadosSolicitudService {

    List<EstadosSolicitudDTO> findAll();

    EstadosSolicitudDTO findById(Long id);


    EstadosSolicitudDTO findByNombreEstado(String nombreEstado);


    EstadosSolicitudDTO create(String nombreEstado);

    EstadosSolicitudDTO update(ActualizarEstadosSolicitudRequests actualizarEstadosSolicitudRequests);

    String delete(Long id);



}

