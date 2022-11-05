package com.enyoi.apimesaayuda.aplicacion.services.impl;


import com.enyoi.apimesaayuda.aplicacion.dtos.SeguimientosDTO;
import com.enyoi.apimesaayuda.aplicacion.dtos.SolicitudesDTO;
import com.enyoi.apimesaayuda.aplicacion.entities.Dependencias;
import com.enyoi.apimesaayuda.aplicacion.entities.Seguimientos;
import com.enyoi.apimesaayuda.aplicacion.entities.Solicitudes;
import com.enyoi.apimesaayuda.aplicacion.repositories.SeguimientosRepository;
import com.enyoi.apimesaayuda.aplicacion.repositories.SolicitudesRepository;
import com.enyoi.apimesaayuda.aplicacion.services.ISeguimientosService;
import com.enyoi.apimesaayuda.base.exceptions.NotDataFound;
import com.enyoi.apimesaayuda.security.entities.Usuarios;
import com.enyoi.apimesaayuda.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeguimientoService implements ISeguimientosService {
    //Utilidades
    private final ModelMapper modelMapper;
    private final DateUtil dateUtil;
    private static final String NOEXISTENDATOS = "datos no existen";

    //Repositorios
    private final SeguimientosRepository seguimientosRepository;
    private final SolicitudesRepository solicitudesRepository;

    @Override
    public List<SeguimientosDTO> findAll() {
        List<Seguimientos> seguimientosList = seguimientosRepository.findAll();
        List<SeguimientosDTO> seguimientosDTOList = new ArrayList<>();

        seguimientosList.forEach(seguimientos -> {
            SeguimientosDTO seguimientosDTO = modelMapper.map(seguimientos, SeguimientosDTO.class);
            seguimientosDTOList.add(seguimientosDTO);
        });

        return seguimientosDTOList;
    }

    /*
    Dependencias = Solicitudes
    solicitudes = sEGUIMIENTO
     */
    @Override
    public Page<SeguimientosDTO> findBySolicitudesId(Long solicitudesId, int page, int size, String columnFilter, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, columnFilter));
        Solicitudes solicitudes = solicitudesRepository.findById(solicitudesId)
                .orElseThrow(() -> new NotDataFound(NOEXISTENDATOS));
        List<SeguimientosDTO> list = seguimientosRepository.findBySolicitudesId(solicitudes, pageable)
                .stream()
                .map(seguimientos -> modelMapper.map(seguimientos, SeguimientosDTO.class))
                .collect(Collectors.toList());
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }
}




