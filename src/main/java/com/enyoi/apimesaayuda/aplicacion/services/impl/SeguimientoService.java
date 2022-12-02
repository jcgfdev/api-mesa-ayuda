package com.enyoi.apimesaayuda.aplicacion.services.impl;


import com.enyoi.apimesaayuda.aplicacion.dtos.SeguimientosDTO;
import com.enyoi.apimesaayuda.aplicacion.entities.*;
import com.enyoi.apimesaayuda.aplicacion.entities.logs.LogsSeguimientos;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.ActualizarSeguimientosRequest;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.SeguimientosRequest;
import com.enyoi.apimesaayuda.aplicacion.repositories.*;
import com.enyoi.apimesaayuda.aplicacion.repositories.logs.LogSeguimientosRepository;
import com.enyoi.apimesaayuda.aplicacion.services.ISeguimientosService;
import com.enyoi.apimesaayuda.base.enums.Acciones;
import com.enyoi.apimesaayuda.base.exceptions.NotActivate;
import com.enyoi.apimesaayuda.base.exceptions.NotDataFound;
import com.enyoi.apimesaayuda.security.entities.Usuarios;
import com.enyoi.apimesaayuda.security.repositories.UsuariosRepository;
import com.enyoi.apimesaayuda.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    private final LogSeguimientosRepository logsSeguimientosRository;
    private static final String NOACTIVADO ="usuario no acticado";

    @Override
    public List<SeguimientosDTO> findAll(String user) {
        Usuarios usuario = usuariosRepository.findByEmail(user)
                .orElseThrow(() -> new NotDataFound(NOACTIVADO));
        if (Boolean.TRUE.equals(usuario.getActivado())) {
        List<Seguimientos> seguimientosList = seguimientosRepository.findAll();
        List<SeguimientosDTO> seguimientosDTOList = new ArrayList<>();

        seguimientosList.forEach(seguimientos -> {
            SeguimientosDTO seguimientosDTO = modelMapper.map(seguimientos, SeguimientosDTO.class);
            seguimientosDTOList.add(seguimientosDTO);
        });

        return seguimientosDTOList;
        }else {
            throw new NotActivate(NOACTIVADO);
        }
    }

    /*
    Dependencias = Solicitudes
    solicitudes = sEGUIMIENTO
     */
    @Override
    public Page<SeguimientosDTO> findBySolicitudesId(Long solicitudesId, int page, int size, String columnFilter, Sort.Direction direction,String user) {
        Usuarios usuario = usuariosRepository.findByEmail(user)
                .orElseThrow(() -> new NotDataFound(NOACTIVADO));
        if (Boolean.TRUE.equals(usuario.getActivado())) {
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
        }else {
            throw new NotActivate(NOACTIVADO);
        }
    }

    @Override
    public SeguimientosDTO crear(SeguimientosRequest seguimientosRequest) {
        Usuarios usuario = usuariosRepository.findByEmail(seguimientosRequest.getUsuario())
                .orElseThrow(() -> new NotDataFound(NOACTIVADO));
        if (Boolean.TRUE.equals(usuario.getActivado())) {
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
        LogsSeguimientos logsSeguimientos = LogsSeguimientos.builder()
                .usuario(seguimientosRequest.getUsuario())
                .acciones(Acciones.CREATED)
                .seguimientos(seguimientosRequest.getDescripcion())
                .fechalog(new Date()).build();
        logsSeguimientosRository.save(logsSeguimientos);
        return modelMapper.map(seguimientosRepository.save(seguimientos), SeguimientosDTO.class);
        }else {
            throw new NotActivate(NOACTIVADO);
        }
    }

    @Override
    public SeguimientosDTO actualizar(ActualizarSeguimientosRequest actualizarSeguimientosRequest) {
        Usuarios usuario = usuariosRepository.findByEmail(actualizarSeguimientosRequest.getUsuario())
                .orElseThrow(() -> new NotDataFound(NOACTIVADO));
        if (Boolean.TRUE.equals(usuario.getActivado())) {
            Optional<Seguimientos> seguimientosOptional = seguimientosRepository
                    .findById(actualizarSeguimientosRequest.getSeguimientoId());
            if (seguimientosOptional.isPresent()) {
                Seguimientos seguimientosGuardar = seguimientosOptional.get();
                Solicitudes solicitudes = solicitudesRepository.findById(actualizarSeguimientosRequest.getSolicitudesId())
                        .orElseThrow(() -> new NotDataFound(NOEXISTENDATOS));
                seguimientosGuardar.setSolicitudesId(solicitudes);
                seguimientosGuardar.setTitulo(actualizarSeguimientosRequest.getTitulo());
                seguimientosGuardar.setFechaRealizado(actualizarSeguimientosRequest.getFechaRealizado());
                seguimientosGuardar.setDescripcion(actualizarSeguimientosRequest.getDescripcion());
                Usuarios usuarios = usuariosRepository.findById(actualizarSeguimientosRequest.getResponsableId())
                        .orElseThrow(() -> new NotDataFound(NOEXISTENDATOS));
                seguimientosGuardar.setResponsableId(usuarios);
                seguimientosGuardar = seguimientosRepository.save(seguimientosGuardar);
                LogsSeguimientos logsSeguimientos = LogsSeguimientos.builder()
                        .usuario(actualizarSeguimientosRequest.getUsuario())
                        .acciones(Acciones.CREATED)
                        .seguimientos(actualizarSeguimientosRequest.getDescripcion())
                        .fechalog(new Date()).build();
                logsSeguimientosRository.save(logsSeguimientos);
                return modelMapper.map(seguimientosGuardar, SeguimientosDTO.class);
            } else {
                throw new NotDataFound("Id de seguimiento no existe");
            }
        }else{
            throw new NotActivate(NOACTIVADO);
        }
    }

    @Override
    public java.lang.String delete(Long id, String user) {
        Usuarios usuario = usuariosRepository.findByEmail(user)
                .orElseThrow(() -> new NotDataFound(NOACTIVADO));
        if (Boolean.TRUE.equals(usuario.getActivado())) {
        Optional<Seguimientos> seguimientosOptional = Optional.ofNullable(seguimientosRepository.findById(id))
                .orElseThrow(() -> new NotDataFound("No existe el estado: " + id));
        if (seguimientosOptional.isPresent()) {
            seguimientosRepository.deleteById(id);
            LogsSeguimientos logsSeguimientos = LogsSeguimientos.builder()
                    .usuario(user)
                    .acciones(Acciones.CREATED)
                    .seguimientos(seguimientosOptional.get().getDescripcion())
                    .fechalog(new Date()).build();
            logsSeguimientosRository.save(logsSeguimientos);
            return modelMapper.map(seguimientosOptional.get(), SeguimientosDTO.class).getId() + "Eliminado con Exito";
        } else {
            throw new NotDataFound(NOEXISTENDATOS);
        }
        } else {
            throw new NotActivate(NOACTIVADO);
        }
    }
}




