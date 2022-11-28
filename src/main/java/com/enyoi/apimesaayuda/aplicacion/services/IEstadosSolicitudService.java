package com.enyoi.apimesaayuda.aplicacion.services;

import com.enyoi.apimesaayuda.aplicacion.dtos.EstadosSolicitudDTO;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.ActualizarEstadosSolicitudRequests;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.CrearEstadosSolicitudRequest;

import java.util.List;
/*
 ** @Auth:ElenaM
 */
public interface IEstadosSolicitudService {

    List<EstadosSolicitudDTO> findAll();

    EstadosSolicitudDTO findById(Long id);


    EstadosSolicitudDTO findByNombreEstado(String nombreEstado);


    EstadosSolicitudDTO create(CrearEstadosSolicitudRequest crearEstadosSolicitudRequest);

    EstadosSolicitudDTO update(ActualizarEstadosSolicitudRequests actualizarEstadosSolicitudRequests);

    String delete(Long id, String usuarios);



}

