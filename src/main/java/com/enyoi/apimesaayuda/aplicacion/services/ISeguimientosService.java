package com.enyoi.apimesaayuda.aplicacion.services;


import com.enyoi.apimesaayuda.aplicacion.dtos.SeguimientosDTO;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.SeguimientosRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ISeguimientosService {
    List<SeguimientosDTO> findAll();

    Page<SeguimientosDTO> findBySolicitudesId(Long solicitudesId,int page, int size, String columnFilter, Sort.Direction direction);


    SeguimientosDTO crear(SeguimientosRequest seguimientosRequest);

    String delete(Long id);


}
