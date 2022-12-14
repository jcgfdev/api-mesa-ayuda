package com.enyoi.apimesaayuda.aplicacion.services;

import com.enyoi.apimesaayuda.aplicacion.dtos.SolicitudesDTO;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.ActualizarSolicitudesRequest;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.SolicitudesRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.Date;

public interface ISolicitudesService {
    Page<SolicitudesDTO> findAll(int page, int size, String columnFilter, Sort.Direction direction, String user);

    SolicitudesDTO findByCodigo(String codigo, String user);

    Page<SolicitudesDTO> findByTipoSolicitudId(Long tiposSolicitudId, int page, int size, String columnFilter, Sort.Direction direction, String user);

    Page<SolicitudesDTO> findByDependenciasId(Long dependenciasId, int page, int size, String columnFilter, Sort.Direction direction, String user);

    Page<SolicitudesDTO> findBySolicitanteId(Long solicitanteId, int page, int size, String columnFilter, Sort.Direction direction, String user);

    SolicitudesDTO findByPrioridad(Long prioridad, String user);

    Page<SolicitudesDTO> findByFechaSolicitudBetween(Date fechaInicio, Date fechaFin, int page, int size, String columnFilter, Sort.Direction direction, String user);

    Page<SolicitudesDTO> findByFechaFinalizadoBetween(Date fechaInicio, Date fechaFin, int page, int size, String columnFilter, Sort.Direction direction, String user);

    Page<SolicitudesDTO> findByEstadoId(Long estadoId, int page, int size, String columnFilter, Sort.Direction direction, String user);

    SolicitudesDTO crear(SolicitudesRequest solicitudesRequest);

    SolicitudesDTO actualizar(ActualizarSolicitudesRequest actualizarSolicitudesRequest);

    String eliminar(Long id, String user);
}
