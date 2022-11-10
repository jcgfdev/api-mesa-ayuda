package com.enyoi.apimesaayuda.aplicacion.services.impl;


import com.enyoi.apimesaayuda.aplicacion.dtos.SeguimientosDTO;
import com.enyoi.apimesaayuda.aplicacion.entities.*;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.ActualizarSeguimientosRequest;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.SeguimientosRequest;
import com.enyoi.apimesaayuda.aplicacion.repositories.*;
import com.enyoi.apimesaayuda.aplicacion.services.ISeguimientosService;
import com.enyoi.apimesaayuda.base.exceptions.NotDataFound;
import com.enyoi.apimesaayuda.security.entities.Usuarios;
import com.enyoi.apimesaayuda.security.repositories.UsuariosRepository;
import com.enyoi.apimesaayuda.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private final UsuariosRepository usuariosRepository;

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

    @Override
    public SeguimientosDTO crear(SeguimientosRequest seguimientosRequest) {

            Seguimientos seguimientos = new Seguimientos();
            Solicitudes solicitudes = solicitudesRepository.findById(seguimientosRequest.getSolicitudesId())
                    .orElseThrow(() -> new NotDataFound(NOEXISTENDATOS));
            seguimientos.setSolicitudesId(solicitudes);
            seguimientos.setTitulo(seguimientosRequest.getTitulo());
            seguimientos.setFechaRealizado(seguimientosRequest.getFechaRealizado());
            seguimientos.setDescripcion(seguimientosRequest.getDescripcion());
            Usuarios usuarios = usuariosRepository.findById(seguimientosRequest.getResponsableId())
                    .orElseThrow(() -> new NotDataFound(NOEXISTENDATOS));
            seguimientos.setResponsableId(usuarios);

            return modelMapper.map(seguimientosRepository.save(seguimientos), SeguimientosDTO.class);

    }

    @Override
    public SeguimientosDTO actualizar(ActualizarSeguimientosRequest actualizarSeguimientosRequest) {
        Optional<Seguimientos> seguimientosOptional = seguimientosRepository
                .findById(actualizarSeguimientosRequest.getSeguimientoId());
        if (seguimientosOptional.isPresent()) {
            Seguimientos seguimientosGuardar = seguimientosOptional.get();
            Solicitudes solicitudes = solicitudesRepository.findById(actualizarSeguimientosRequest.getSolicitudesId())
                    .orElseThrow(() -> new NotDataFound(NOEXISTENDATOS));
            seguimientosGuardar.setTitulo(actualizarSeguimientosRequest.getTitulo());
            seguimientosGuardar.setFechaRealizado(actualizarSeguimientosRequest.getFechaRealizado());
            seguimientosGuardar.setDescripcion(actualizarSeguimientosRequest.getDescripcion());
            Usuarios usuarios = usuariosRepository.findById(actualizarSeguimientosRequest.getResponsableId())
                    .orElseThrow(() -> new NotDataFound(NOEXISTENDATOS));
            seguimientosGuardar = seguimientosRepository.save(seguimientosGuardar);
            return modelMapper.map(seguimientosGuardar, SeguimientosDTO.class);
        } else {
            throw new NotDataFound("Id de seguimiento no existe");
        }

    }

    @Override
    public java.lang.String delete(Long id) {
        Optional<Seguimientos> seguimientosOptional = Optional.ofNullable(seguimientosRepository.findById(id))
                .orElseThrow(() -> new NotDataFound("No existe el estado: "+ id ) );
    seguimientosRepository.deleteById(id);

        return seguimientosOptional.get() + "Eliminado con Exito";
    }
}




