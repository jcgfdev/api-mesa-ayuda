package com.enyoi.apimesaayuda.aplicacion.services;

import com.enyoi.apimesaayuda.aplicacion.dtos.EstadosSolicitudDTO;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.ActualizarEstadosSolicitudRequests;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.CrearEstadosSolicitudRequest;

import java.util.List;
/*
 ** @Auth:ElenaM
 */
public interface IEstadosSolicitudService {

    List<EstadosSolicitudDTO> findAll(String user);

    EstadosSolicitudDTO findById(Long id, String user);


    EstadosSolicitudDTO findByNombreEstado(String nombreEstado, String user);


    EstadosSolicitudDTO create(CrearEstadosSolicitudRequest crearEstadosSolicitudRequest);

    EstadosSolicitudDTO update(ActualizarEstadosSolicitudRequests actualizarEstadosSolicitudRequests);

    String delete(Long id, String user);



}

