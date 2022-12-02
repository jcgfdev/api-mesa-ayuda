package com.enyoi.apimesaayuda.aplicacion.services.impl;

import com.enyoi.apimesaayuda.aplicacion.dtos.EstadosSolicitudDTO;
import com.enyoi.apimesaayuda.aplicacion.entities.EstadosSolicitud;
import com.enyoi.apimesaayuda.aplicacion.entities.logs.LogsEstadosSolicitud;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.ActualizarEstadosSolicitudRequests;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.CrearEstadosSolicitudRequest;
import com.enyoi.apimesaayuda.aplicacion.repositories.EstadosSolicitudRepository;
import com.enyoi.apimesaayuda.aplicacion.repositories.logs.LogsEstadosSolicitudRepository;
import com.enyoi.apimesaayuda.aplicacion.services.IEstadosSolicitudService;
import com.enyoi.apimesaayuda.base.enums.Acciones;
import com.enyoi.apimesaayuda.base.exceptions.AlreadyExists;

import com.enyoi.apimesaayuda.base.exceptions.NotActivate;
import com.enyoi.apimesaayuda.base.exceptions.NotDataFound;
import com.enyoi.apimesaayuda.security.entities.Usuarios;
import com.enyoi.apimesaayuda.security.repositories.UsuariosRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EstadosSolicitudService implements IEstadosSolicitudService {
    private final ModelMapper modelMapper;

    private static final String NOACTIVADO = "Error: Usuario no Activado.";

    private final EstadosSolicitudRepository estadosSolicitudRepository;

    private final LogsEstadosSolicitudRepository logsEstadosSolicitudRepository;

    private final UsuariosRepository usuariosRepository;

    /*
     **@Auth: D David Galeano Puche
     */
    @Override
    public List<EstadosSolicitudDTO> findAll(String user) {
        Usuarios usuario = usuariosRepository.findByEmail(user)
                .orElseThrow(() -> new NotDataFound(NOACTIVADO));
        if (Boolean.TRUE.equals(usuario.getActivado())) {
        List<EstadosSolicitud> estadosSolicitudList = estadosSolicitudRepository.findAll();
        List<EstadosSolicitudDTO> estadosSolicitudDTOList = new ArrayList<>();

        estadosSolicitudList.forEach(estadosSolicitud -> {
            EstadosSolicitudDTO estadosSolicitudDTO = modelMapper.map(estadosSolicitud, EstadosSolicitudDTO.class);
            estadosSolicitudDTOList.add(estadosSolicitudDTO);
        });

        return estadosSolicitudDTOList;
    }else {
            throw new NotActivate(NOACTIVADO);
        }
    }
    /*
     **@Auth: D David Galeano Puche
     */
    @Override
    public EstadosSolicitudDTO findById(Long id, String user) {
        Usuarios usuario = usuariosRepository.findByEmail(user)
                .orElseThrow(() -> new NotDataFound(NOACTIVADO));
        if (Boolean.TRUE.equals(usuario.getActivado())) {
        Optional<EstadosSolicitud> estadosSolicitudOptional = estadosSolicitudRepository.findById(id);
        EstadosSolicitud estadosSolicitud;
        if (estadosSolicitudOptional.isPresent()) {
            estadosSolicitud = estadosSolicitudOptional.get();
            return modelMapper.map(estadosSolicitud, EstadosSolicitudDTO.class);
        } else {
            throw new NotDataFound("estado no existe");
        }

    }else {
            throw new NotActivate(NOACTIVADO);
        }
    }

    /*
     **@Auth: D David Galeano Puche
     */
    @Override
    public EstadosSolicitudDTO findByNombreEstado(String nombreEstado, String user) {
        Usuarios usuario = usuariosRepository.findByEmail(user)
                .orElseThrow(() -> new NotDataFound(NOACTIVADO));
        if (Boolean.TRUE.equals(usuario.getActivado())) {
        Optional<EstadosSolicitud> estadosSolicitudOptional = estadosSolicitudRepository.findByNombreEstado(nombreEstado);
        EstadosSolicitud estadosSolicitud;
        if (estadosSolicitudOptional.isPresent()) {
            estadosSolicitud = estadosSolicitudOptional.get();
            return modelMapper.map(estadosSolicitud, EstadosSolicitudDTO.class);
        } else {
            throw new NotDataFound("Nombre estado no existe");
        }

    }else {
            throw new NotActivate(NOACTIVADO);
        }
    }


    /*
     **@Auth: D David Galeano Puche
     */
    @Override
    public EstadosSolicitudDTO create(CrearEstadosSolicitudRequest crearEstadosSolicitudRequest) {
        Usuarios usuario = usuariosRepository.findByEmail(crearEstadosSolicitudRequest.getUsuario())
                .orElseThrow(() -> new NotDataFound(NOACTIVADO));
        if (Boolean.TRUE.equals(usuario.getActivado())) {
        Optional<EstadosSolicitud> estadosSolicitudOptional = estadosSolicitudRepository.findByNombreEstado(crearEstadosSolicitudRequest.getNombreEstado());
        if (estadosSolicitudOptional.isPresent()) {
            throw new AlreadyExists("nombre estado solicitud ya existe. ");
        } else {
            EstadosSolicitud estadosSolicitud = new EstadosSolicitud();
            estadosSolicitud.setNombreEstado(crearEstadosSolicitudRequest.getNombreEstado());
            LogsEstadosSolicitud logsEstadosSolicitud = LogsEstadosSolicitud.builder()
                    .usuario(crearEstadosSolicitudRequest.getUsuario())
                    .acciones(Acciones.CREATED)
                    .estadosSolicitud(estadosSolicitud.getNombreEstado())
                    .fechalog(new Date()).build();
            logsEstadosSolicitudRepository.save(logsEstadosSolicitud);
            return modelMapper.map(estadosSolicitudRepository.save(estadosSolicitud), EstadosSolicitudDTO.class);
        }

    }else {
            throw new NotActivate(NOACTIVADO);
        }
    }

    /*
     **@Auth: D David Galeano Puche
     */
    @Override
    public EstadosSolicitudDTO update(ActualizarEstadosSolicitudRequests actualizarEstadosSolicitudRequests) {
        Usuarios usuario = usuariosRepository.findByEmail(actualizarEstadosSolicitudRequests.getUsuario())
                .orElseThrow(() -> new NotDataFound(NOACTIVADO));
        if (Boolean.TRUE.equals(usuario.getActivado())) {
        Optional<EstadosSolicitud> estadosSolicitudOptional = estadosSolicitudRepository.findById(actualizarEstadosSolicitudRequests.getEstadosSolicitudId());
        if (estadosSolicitudOptional.isPresent()) {
            EstadosSolicitud updateEstado = estadosSolicitudOptional.get();
            updateEstado.setNombreEstado(actualizarEstadosSolicitudRequests.getNombreEstado() != null
                    ? actualizarEstadosSolicitudRequests.getNombreEstado()
                    : updateEstado.getNombreEstado());
            LogsEstadosSolicitud logsEstadosSolicitud = LogsEstadosSolicitud.builder()
                    .usuario(actualizarEstadosSolicitudRequests.getUsuario())
                    .acciones(Acciones.UPDATE)
                    .estadosSolicitud(updateEstado.getNombreEstado())
                    .fechalog(new Date())
                    .build();
            logsEstadosSolicitudRepository.save(logsEstadosSolicitud);
            return modelMapper.map(estadosSolicitudRepository.save(updateEstado), EstadosSolicitudDTO.class);
        } else {
            throw new NotDataFound("Solicitud no existe");
        }

    }else {
            throw new NotActivate(NOACTIVADO);
        }
    }

    /*
     **@Auth: D David Galeano Puche
     */
    @Override
    public String delete(Long id, String user) {
        Usuarios usuario = usuariosRepository.findByEmail(user)
                .orElseThrow(() -> new NotDataFound(NOACTIVADO));
        if (Boolean.TRUE.equals(usuario.getActivado())) {
            Optional<EstadosSolicitud> estadosSolicitudOptional = Optional.ofNullable(estadosSolicitudRepository.findById(id)
                    .orElseThrow(() -> new NotDataFound("No existe el estado: " + id)));
            if (estadosSolicitudOptional.isPresent()) {
                estadosSolicitudRepository.deleteById(id);
                LogsEstadosSolicitud logsEstadosSolicitud = LogsEstadosSolicitud.builder()
                        .usuario(user)
                        .acciones(Acciones.DELETE)
                        .estadosSolicitud(estadosSolicitudOptional.get().getNombreEstado())
                        .fechalog(new Date()).build();
                logsEstadosSolicitudRepository.save(logsEstadosSolicitud);
                return modelMapper.map(estadosSolicitudOptional.get(), EstadosSolicitudDTO.class).getNombreEstado() + " Eliminado con Exito ";
            } else {
                throw new NotDataFound("Estado no existe");
            }

        } else {
            throw new NotActivate(NOACTIVADO);
        }
    }


}