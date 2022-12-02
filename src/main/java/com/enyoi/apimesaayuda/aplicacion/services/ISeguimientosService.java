package com.enyoi.apimesaayuda.aplicacion.services;


import com.enyoi.apimesaayuda.aplicacion.dtos.SeguimientosDTO;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.ActualizarSeguimientosRequest;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.SeguimientosRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ISeguimientosService {
    List<SeguimientosDTO> findAll(String user);

    Page<SeguimientosDTO> findBySolicitudesId(Long solicitudesId, int page, int size, String columnFilter, Sort.Direction direction,String user);

    SeguimientosDTO crear(SeguimientosRequest seguimientosRequest);

    SeguimientosDTO actualizar(ActualizarSeguimientosRequest actualizarSeguimientosRequest);

    String delete(Long id, String user);


}
