package com.enyoi.apimesaayuda.aplicacion.services;

import com.enyoi.apimesaayuda.aplicacion.dtos.EstadosSolicitudDTO;
import com.enyoi.apimesaayuda.aplicacion.entities.EstadosSolicitud;

import java.util.List;

public interface IEstadosSolicitudService {

    List<EstadosSolicitudDTO> findAll();

    EstadosSolicitudDTO findById(Long id);

    EstadosSolicitudDTO findByNombreEstado(String nombreEstado);

    EstadosSolicitudDTO create(String nombreEstado);

    EstadosSolicitudDTO updateId(EstadosSolicitud estadosSolicitud);

    String delete(Long id);



}
