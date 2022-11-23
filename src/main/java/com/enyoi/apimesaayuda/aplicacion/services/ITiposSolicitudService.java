package com.enyoi.apimesaayuda.aplicacion.services;

import com.enyoi.apimesaayuda.aplicacion.dtos.TiposSolicitudDTO;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.TiposSolicitudRequest;

import java.util.List;

public interface ITiposSolicitudService {

    List<TiposSolicitudDTO> findAll();

    TiposSolicitudDTO findById(Long id);

    TiposSolicitudDTO findByTipoSolicitud(String tipoSolicitud);

    TiposSolicitudDTO create(String tipoSolicitud);

    TiposSolicitudDTO update(TiposSolicitudRequest tiposSolicitudRequests);

    String delete(Long id);

}
