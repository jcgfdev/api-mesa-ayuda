package com.enyoi.apimesaayuda.aplicacion.services;

import com.enyoi.apimesaayuda.aplicacion.dtos.TiposSolicitudDTO;
import com.enyoi.apimesaayuda.aplicacion.entities.TiposSolicitud;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.ActualizarTiposSolicitudRequests;

import java.util.List;

public interface ITiposSolicitudService {
    List<TiposSolicitudDTO> finAll();

    List<TiposSolicitudDTO> findAll();

    TiposSolicitudDTO findById(Long id);

    TiposSolicitudDTO findByTipoSolicitud(String tipoSolicitud);

    TiposSolicitudDTO create(String nombreTipoSolicitud);

    TiposSolicitudDTO update(ActualizarTiposSolicitudRequests actualizarTiposSolicitudRequests);

    String delete(Long id);

}
