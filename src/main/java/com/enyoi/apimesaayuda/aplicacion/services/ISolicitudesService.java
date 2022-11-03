package com.enyoi.apimesaayuda.aplicacion.services;

import com.enyoi.apimesaayuda.aplicacion.dtos.SolicitudesDTO;
import com.enyoi.apimesaayuda.aplicacion.entities.Dependencias;
import com.enyoi.apimesaayuda.aplicacion.entities.EstadosSolicitud;
import com.enyoi.apimesaayuda.aplicacion.entities.TiposSolicitud;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.ActualizarSolicitudesRequest;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.SolicitudesRequest;
import com.enyoi.apimesaayuda.security.entities.Usuarios;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.text.ParseException;
import java.util.Date;

public interface ISolicitudesService {
    Page<SolicitudesDTO> findAll(int page, int size, String columnFilter, Sort.Direction direction);

    SolicitudesDTO findByCodigo(String codigo);

    Page<SolicitudesDTO> findByTipoSolicitudId(Long tiposSolicitudId, int page, int size, String columnFilter, Sort.Direction direction);

    Page<SolicitudesDTO> findByDependenciasId(Long dependenciasId, int page, int size, String columnFilter, Sort.Direction direction);

    Page<SolicitudesDTO> findBySolicitanteId(Long solicitanteId, int page, int size, String columnFilter, Sort.Direction direction);

    Page<SolicitudesDTO> findByFechaSolicitudBetween(Date fechaInicio, Date fechaFin, int page, int size, String columnFilter, Sort.Direction direction);

    Page<SolicitudesDTO> findByFechaFinalizadoBetween(Date fechaInicio, Date fechaFin, int page, int size, String columnFilter, Sort.Direction direction);

    Page<SolicitudesDTO> findByEstadoId(Long estadoId, int page, int size, String columnFilter, Sort.Direction direction);

    SolicitudesDTO crear(SolicitudesRequest solicitudesRequest);

    SolicitudesDTO actualizar(ActualizarSolicitudesRequest actualizarSolicitudesRequest);

    String eliminar(Long id);
}
