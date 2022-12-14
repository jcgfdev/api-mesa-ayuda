package com.enyoi.apimesaayuda.aplicacion.services.impl;

import com.enyoi.apimesaayuda.aplicacion.dtos.SolicitudesDTO;
import com.enyoi.apimesaayuda.aplicacion.entities.Dependencias;
import com.enyoi.apimesaayuda.aplicacion.entities.EstadosSolicitud;
import com.enyoi.apimesaayuda.aplicacion.entities.Solicitudes;
import com.enyoi.apimesaayuda.aplicacion.entities.TiposSolicitud;
import com.enyoi.apimesaayuda.aplicacion.entities.logs.LogsSolicitudes;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.ActualizarSolicitudesRequest;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.SolicitudesRequest;
import com.enyoi.apimesaayuda.aplicacion.repositories.DependenciasRepository;
import com.enyoi.apimesaayuda.aplicacion.repositories.EstadosSolicitudRepository;
import com.enyoi.apimesaayuda.aplicacion.repositories.SolicitudesRepository;
import com.enyoi.apimesaayuda.aplicacion.repositories.TiposSolicitudRepository;
import com.enyoi.apimesaayuda.aplicacion.repositories.logs.LogsSolicitudesRepository;
import com.enyoi.apimesaayuda.aplicacion.services.ISolicitudesService;
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

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SolicitudesService implements ISolicitudesService {
    //Utilidades
    private final ModelMapper modelMapper;
    private final DateUtil dateUtil;
    private static final String NOEXISTENDATOS = "datos no existen";
    private static final String NOACTIVADO = "Error: Usuario no Activado.";

    //Repositorios
    private final SolicitudesRepository solicitudesRepository;
    private final TiposSolicitudRepository tiposSolicitudRepository;
    private final DependenciasRepository dependenciasRepository;
    private final UsuariosRepository usuariosRepository;
    private final EstadosSolicitudRepository estadosSolicitudRepository;
    private final LogsSolicitudesRepository logsSolicitudesRepository;

    @Override
    public Page<SolicitudesDTO> findAll(int page, int size, String columnFilter, Sort.Direction direction, String user) {
        Usuarios usuario = usuariosRepository.findByEmail(user)
                .orElseThrow(() -> new NotDataFound(NOACTIVADO));
        if (Boolean.TRUE.equals(usuario.getActivado())) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, columnFilter));
        List<SolicitudesDTO> list = solicitudesRepository.findAll(pageable)
                .stream()
                .map(solicitudes -> modelMapper.map(solicitudes, SolicitudesDTO.class))
                .collect(Collectors.toList());
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }else {
            throw new NotActivate(NOACTIVADO);
        }
    }

    @Override
    public SolicitudesDTO findByCodigo(String codigo, String user) {
        Usuarios usuario = usuariosRepository.findByEmail(user)
                .orElseThrow(() -> new NotDataFound(NOACTIVADO));
        if (Boolean.TRUE.equals(usuario.getActivado())) {
        Optional<Solicitudes> solicitudesOptional = solicitudesRepository.findByCodigo(codigo);
        Solicitudes solicitudes;
        if (solicitudesOptional.isPresent()) {
            solicitudes = solicitudesOptional.get();
            return modelMapper.map(solicitudes, SolicitudesDTO.class);
        } else {
            throw new NotDataFound(NOEXISTENDATOS);
        }

    }else {
            throw new NotActivate(NOACTIVADO);
        }
    }

    @Override
    public Page<SolicitudesDTO> findByTipoSolicitudId(Long tiposSolicitudId, int page, int size, String columnFilter, Sort.Direction direction, String user) {
        Usuarios usuario = usuariosRepository.findByEmail(user)
                .orElseThrow(() -> new NotDataFound(NOACTIVADO));
        if (Boolean.TRUE.equals(usuario.getActivado())) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, columnFilter));
        TiposSolicitud tiposSolicitud = tiposSolicitudRepository.findById(tiposSolicitudId)
                .orElseThrow(() -> new NotDataFound(NOEXISTENDATOS));
        List<SolicitudesDTO> list = solicitudesRepository.findByTipoSolicitudId(tiposSolicitud, pageable)
                .stream()
                .map(solicitudes -> modelMapper.map(solicitudes, SolicitudesDTO.class))
                .collect(Collectors.toList());
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }else {
            throw new NotActivate(NOACTIVADO);
        }
    }

    @Override
    public Page<SolicitudesDTO> findByDependenciasId(Long dependenciasId, int page, int size, String columnFilter, Sort.Direction direction, String user) {
        Usuarios usuario = usuariosRepository.findByEmail(user)
                .orElseThrow(() -> new NotDataFound(NOACTIVADO));
        if (Boolean.TRUE.equals(usuario.getActivado())) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, columnFilter));
        Dependencias dependencias = dependenciasRepository.findById(dependenciasId)
                .orElseThrow(() -> new NotDataFound(NOEXISTENDATOS));
        List<SolicitudesDTO> list = solicitudesRepository.findByDependenciasId(dependencias, pageable)
                .stream()
                .map(solicitudes -> modelMapper.map(solicitudes, SolicitudesDTO.class))
                .collect(Collectors.toList());
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }else {
            throw new NotActivate(NOACTIVADO);
        }
    }

    @Override
    public Page<SolicitudesDTO> findBySolicitanteId(Long solicitanteId, int page, int size, String columnFilter, Sort.Direction direction, String user) {
        Usuarios usuario = usuariosRepository.findByEmail(user)
                .orElseThrow(() -> new NotDataFound(NOACTIVADO));
        if (Boolean.TRUE.equals(usuario.getActivado())) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, columnFilter));
        Usuarios usuarios = usuariosRepository.findById(solicitanteId)
                .orElseThrow(() -> new NotDataFound(NOEXISTENDATOS));
        List<SolicitudesDTO> list = solicitudesRepository.findBySolicitanteId(usuarios, pageable)
                .stream()
                .map(solicitudes -> modelMapper.map(solicitudes, SolicitudesDTO.class))
                .collect(Collectors.toList());
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }else {
            throw new NotActivate(NOACTIVADO);
        }
    }

    @Override
    public SolicitudesDTO findByPrioridad(Long prioridad, String user) {
        Usuarios usuario = usuariosRepository.findByEmail(user)
                .orElseThrow(() -> new NotDataFound(NOACTIVADO));
        if (Boolean.TRUE.equals(usuario.getActivado())) {
            Optional<Solicitudes> solicitudesOptional = solicitudesRepository.findByPrioridad(prioridad);
            Solicitudes solicitudes;
            if (solicitudesOptional.isPresent()) {
                solicitudes = solicitudesOptional.get();
                return modelMapper.map(solicitudes, SolicitudesDTO.class);
            } else {
                throw new NotDataFound(NOEXISTENDATOS);
            }

        }else {
            throw new NotActivate(NOACTIVADO);
        }
    }

    @Override
    public Page<SolicitudesDTO> findByFechaSolicitudBetween(Date fechaInicio, Date fechaFin, int page, int size, String columnFilter, Sort.Direction direction, String user) {
        Usuarios usuario = usuariosRepository.findByEmail(user)
                .orElseThrow(() -> new NotDataFound(NOACTIVADO));
        if (Boolean.TRUE.equals(usuario.getActivado())) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, columnFilter));
        List<SolicitudesDTO> list = solicitudesRepository.findByFechaSolicitudBetween(fechaInicio, fechaFin, pageable)
                .stream()
                .map(solicitudes -> modelMapper.map(solicitudes, SolicitudesDTO.class))
                .collect(Collectors.toList());
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }else {
            throw new NotActivate(NOACTIVADO);
        }
    }

    @Override
    public Page<SolicitudesDTO> findByFechaFinalizadoBetween(Date fechaInicio, Date fechaFin, int page, int size, String columnFilter, Sort.Direction direction, String user) {
        Usuarios usuario = usuariosRepository.findByEmail(user)
                .orElseThrow(() -> new NotDataFound(NOACTIVADO));
        if (Boolean.TRUE.equals(usuario.getActivado())) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, columnFilter));
        List<SolicitudesDTO> list = solicitudesRepository.findByFechaFinalizadoBetween(fechaInicio, fechaFin, pageable)
                .stream()
                .map(solicitudes -> modelMapper.map(solicitudes, SolicitudesDTO.class))
                .collect(Collectors.toList());
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }else {
            throw new NotActivate(NOACTIVADO);
        }
    }

    @Override
    public Page<SolicitudesDTO> findByEstadoId(Long estadoId, int page, int size, String columnFilter, Sort.Direction direction, String user) {
        Usuarios usuario = usuariosRepository.findByEmail(user)
                .orElseThrow(() -> new NotDataFound(NOACTIVADO));
        if (Boolean.TRUE.equals(usuario.getActivado())) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, columnFilter));
        EstadosSolicitud estadosSolicitud = estadosSolicitudRepository.findById(estadoId)
                .orElseThrow(() -> new NotDataFound(NOEXISTENDATOS));
        List<SolicitudesDTO> list = solicitudesRepository.findByEstadoId(estadosSolicitud, pageable)
                .stream()
                .map(solicitudes -> modelMapper.map(solicitudes, SolicitudesDTO.class))
                .collect(Collectors.toList());
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }else {
            throw new NotActivate(NOACTIVADO);
        }
    }

    @Override
    public SolicitudesDTO crear(SolicitudesRequest solicitudesRequest) {
        var A = 1;
        Usuarios usuario = usuariosRepository.findByEmail(solicitudesRequest.getUsuario())
                .orElseThrow(() -> new NotDataFound(NOACTIVADO));
        if (Boolean.TRUE.equals(usuario.getActivado())) {
        Optional<Solicitudes> solicitudesOptional = solicitudesRepository.findByCodigo(solicitudesRequest.getCodigo());
        if (solicitudesOptional.isPresent()) {
            throw new NotDataFound("codigo de solicitud ya ha sido asignado");
        } else {
            Solicitudes solicitudes = new Solicitudes();
            solicitudes.setCodigo(solicitudesRequest.getCodigo());
            TiposSolicitud tiposSolicitud = tiposSolicitudRepository.findById(solicitudesRequest.getTipoSolicitudId())
                    .orElseThrow(() -> new NotDataFound(NOEXISTENDATOS));
            solicitudes.setTipoSolicitudId(tiposSolicitud);

            Dependencias dependencias = dependenciasRepository.findById(solicitudesRequest.getDependenciasId())
                    .orElseThrow(() -> new NotDataFound(NOEXISTENDATOS));
            solicitudes.setDependenciasId(dependencias);
            Usuarios usuarios = usuariosRepository.findById(solicitudesRequest.getSolicitanteId())
                    .orElseThrow(() -> new NotDataFound(NOEXISTENDATOS));
            solicitudes.setSolicitanteId(usuarios);
            solicitudes.setTitulo(solicitudesRequest.getTitulo());
            solicitudes.setDescripcion(solicitudesRequest.getDescripcion());
            solicitudes.setFechaSolicitud(solicitudesRequest.getFechaSolicitud());
            solicitudes.setFechaFinalizado(solicitudesRequest.getFechaFinalizado());
            EstadosSolicitud estadosSolicitud = estadosSolicitudRepository.findById(solicitudesRequest.getEstadoId())
                    .orElseThrow(() -> new NotDataFound(NOEXISTENDATOS));
            solicitudes.setEstadoId(estadosSolicitud);
            solicitudes.setPrioridad((long) A);
            LogsSolicitudes logsSolicitudes = LogsSolicitudes.builder()
                    .usuario(solicitudesRequest.getUsuario())
                    .acciones(Acciones.CREATED)
                    .solicitudes(solicitudesRequest.getDescripcion())
                    .fechalog(new Date()).build();
            logsSolicitudesRepository.save(logsSolicitudes);
            return modelMapper.map(solicitudesRepository.save(solicitudes), SolicitudesDTO.class);
        }
    }else {
            throw new NotActivate(NOACTIVADO);
        }
    }

    @Override
    public SolicitudesDTO actualizar(ActualizarSolicitudesRequest actualizarSolicitudesRequest) {
        Usuarios usuario = usuariosRepository.findByEmail(actualizarSolicitudesRequest.getUsuario())
                .orElseThrow(() -> new NotDataFound(NOACTIVADO));
        if (Boolean.TRUE.equals(usuario.getActivado())) {
        Optional<Solicitudes> solicitudesOptional = solicitudesRepository.findByCodigo(actualizarSolicitudesRequest.getCodigo());
        if (solicitudesOptional.isPresent()) {
            Solicitudes solicitudesGuardar = solicitudesOptional.get();
            solicitudesGuardar.setCodigo(actualizarSolicitudesRequest.getCodigo());
            solicitudesGuardar.setDescripcion(actualizarSolicitudesRequest.getDescripcion());
            solicitudesGuardar.setFechaSolicitud(actualizarSolicitudesRequest.getFechaSolicitud());
            solicitudesGuardar.setFechaFinalizado(actualizarSolicitudesRequest.getFechaFinalizado());
            solicitudesGuardar.setTitulo(actualizarSolicitudesRequest.getTitulo());
            if ((actualizarSolicitudesRequest.getPrioridad()<=3) && (actualizarSolicitudesRequest.getPrioridad()>=1)) {
                solicitudesGuardar.setPrioridad(actualizarSolicitudesRequest.getPrioridad());
            }else{
                throw new NotDataFound("-Prioridad debe estar entre 1 y 3; 1 menos a 24hrs, 2 entre 24 y 47hrs y 3 mayor a 48hrs ");
            }
            Dependencias dependencias = dependenciasRepository.findById(actualizarSolicitudesRequest.getDependenciasId())
                    .orElseThrow(() -> new NotDataFound(NOEXISTENDATOS));
            solicitudesGuardar.setDependenciasId(dependencias);
            EstadosSolicitud estadosSolicitud = estadosSolicitudRepository.findById(actualizarSolicitudesRequest.getEstadoId())
                    .orElseThrow(() -> new NotDataFound(NOEXISTENDATOS));
            solicitudesGuardar.setEstadoId(estadosSolicitud);
            Usuarios usuarios = usuariosRepository.findById(actualizarSolicitudesRequest.getSolicitanteId())
                    .orElseThrow(() -> new NotDataFound(NOEXISTENDATOS));
            solicitudesGuardar.setSolicitanteId(usuarios);
            TiposSolicitud tiposSolicitud = tiposSolicitudRepository.findById(actualizarSolicitudesRequest.getTipoSolicitudId())
                    .orElseThrow(() -> new NotDataFound(NOEXISTENDATOS));
            solicitudesGuardar.setTipoSolicitudId(tiposSolicitud);
            solicitudesGuardar = solicitudesRepository.save(solicitudesGuardar);
            LogsSolicitudes logsSolicitudes = LogsSolicitudes.builder()
                    .usuario(actualizarSolicitudesRequest.getUsuario())
                    .acciones(Acciones.UPDATE)
                    .solicitudes(actualizarSolicitudesRequest.getDescripcion())
                    .fechalog(new Date()).build();
            logsSolicitudesRepository.save(logsSolicitudes);
            return modelMapper.map(solicitudesGuardar, SolicitudesDTO.class);
        } else {
            throw new NotDataFound("codigo de solicitud no existe");
        }
    }else {
            throw new NotActivate(NOACTIVADO);
        }
    }

    @Override
    public String eliminar(Long id, String user) {
        Usuarios usuario = usuariosRepository.findByEmail(user)
                .orElseThrow(() -> new NotDataFound(NOACTIVADO));
        if (Boolean.TRUE.equals(usuario.getActivado())) {
        Optional<Solicitudes> solicitudesOptional = Optional.ofNullable(solicitudesRepository.findById(id)
                .orElseThrow(() -> new NotDataFound(" Solicitud No existe: " + id)));
        if (solicitudesOptional.isPresent()) {
            solicitudesRepository.deleteById(id);
            LogsSolicitudes logsSolicitudes = LogsSolicitudes.builder()
                    .usuario(user)
                    .acciones(Acciones.DELETE)
                    .solicitudes(solicitudesOptional.get().getDescripcion())
                    .fechalog(new Date()).build();
            logsSolicitudesRepository.save(logsSolicitudes);

            return modelMapper.map(solicitudesOptional.get(), SolicitudesDTO.class).getId() + "Eliminado con Exito";
        } else {
            throw new NotDataFound(NOEXISTENDATOS);
        }
    }else {
            throw new NotActivate(NOACTIVADO);
        }
    }



}
