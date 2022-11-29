package com.enyoi.apimesaayuda.aplicacion.services.impl;

import com.enyoi.apimesaayuda.aplicacion.dtos.TiposSolicitudDTO;
import com.enyoi.apimesaayuda.aplicacion.entities.TiposSolicitud;
import com.enyoi.apimesaayuda.aplicacion.entities.logs.LogsTiposSolicitud;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.CrearTiposSolicitudRequest;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.TiposSolicitudRequest;
import com.enyoi.apimesaayuda.aplicacion.repositories.TiposSolicitudRepository;
import com.enyoi.apimesaayuda.aplicacion.repositories.logs.LogsTiposSolicitudRepository;
import com.enyoi.apimesaayuda.aplicacion.services.ITiposSolicitudService;
import com.enyoi.apimesaayuda.base.enums.Acciones;
import com.enyoi.apimesaayuda.base.exceptions.AlreadyExists;
import com.enyoi.apimesaayuda.base.exceptions.NotDataFound;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TiposSolicitudService implements ITiposSolicitudService {
    private final ModelMapper modelMapper;
    private final LogsTiposSolicitudRepository logsTiposSolicitudRepository;
    private static final String NOEXISTENDATOS = "No esxisten datos";
    private final TiposSolicitudRepository tiposSolicitudRepository;


    @Override
    public List<TiposSolicitudDTO> findAll() {
        List<TiposSolicitud> tiposSolicitudList = tiposSolicitudRepository.findAll();
        List<TiposSolicitudDTO> tiposSolicitudDTOList = new ArrayList<>();
        tiposSolicitudList.forEach(tiposSolicitud -> {
            TiposSolicitudDTO tiposSolicitudDTO = modelMapper.map(tiposSolicitud, TiposSolicitudDTO.class);
            tiposSolicitudDTOList.add(tiposSolicitudDTO);
        });

        return tiposSolicitudDTOList;
    }

    @Override
    public TiposSolicitudDTO findById(Long id) {
        Optional<TiposSolicitud> tiposSolicitudOptional = tiposSolicitudRepository.findById(id);
        TiposSolicitud tiposSolicitud;
        if (tiposSolicitudOptional.isPresent()) {
            tiposSolicitud = tiposSolicitudOptional.get();
            return modelMapper.map(tiposSolicitud, TiposSolicitudDTO.class);
        } else {
            throw new NotDataFound(NOEXISTENDATOS);
        }

    }

    @Override
    public TiposSolicitudDTO findByTipoSolicitud(String tipoSolicitud) {
        Optional<TiposSolicitud> tiposSolicitudOptional = tiposSolicitudRepository.findByTipoSolicitud(tipoSolicitud);
        TiposSolicitud tiposSolicitud;
        if (tiposSolicitudOptional.isPresent()) {
            tiposSolicitud = tiposSolicitudOptional.get();
            return modelMapper.map(tiposSolicitud, TiposSolicitudDTO.class);
        } else {
            throw new NotDataFound(NOEXISTENDATOS);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TiposSolicitudDTO create(CrearTiposSolicitudRequest crearTiposSolicitudRequest) {
        Optional<TiposSolicitud> tiposSolicitudOptional = tiposSolicitudRepository
                .findByTipoSolicitud(crearTiposSolicitudRequest.getTipoSolicitud());
        if (tiposSolicitudOptional.isPresent()) {
            throw new AlreadyExists("Tipo solicitud ya existe");
        } else {
            TiposSolicitud tiposSolicitud = new TiposSolicitud();
            tiposSolicitud.setTipoSolicitud(crearTiposSolicitudRequest.getTipoSolicitud());
            LogsTiposSolicitud logsTiposSolicitud = LogsTiposSolicitud.builder()
                    .usuario(crearTiposSolicitudRequest.getUsuario())
                    .acciones(Acciones.CREATED)
                    .tiposSolicitud(tiposSolicitud.getTipoSolicitud())
                    .fechalog(new Date()).build();
            logsTiposSolicitudRepository.save(logsTiposSolicitud);
            return modelMapper.map(tiposSolicitudRepository.save(tiposSolicitud), TiposSolicitudDTO.class);
        }

    }

    @Override
    public TiposSolicitudDTO update(TiposSolicitudRequest tiposSolicitudRequests) {
        Optional<TiposSolicitud> tiposSolicitudOptional = tiposSolicitudRepository.findById(tiposSolicitudRequests.getTipoSolicitudId());
        if (tiposSolicitudOptional.isPresent()) {
            TiposSolicitud tipoSolicitudGuardar = tiposSolicitudOptional.get();
            tipoSolicitudGuardar.setTipoSolicitud(tiposSolicitudRequests.getTipoSolicitud() != null
                    ? tiposSolicitudRequests.getTipoSolicitud()
                    : tipoSolicitudGuardar.getTipoSolicitud());
            LogsTiposSolicitud logsTiposSolicitud = LogsTiposSolicitud.builder()
                    .usuario(tiposSolicitudRequests.getUsuario())
                    .acciones(Acciones.UPDATE)
                    .tiposSolicitud(tiposSolicitudRequests.getTipoSolicitud())
                    .fechalog(new Date()).build();
            logsTiposSolicitudRepository.save(logsTiposSolicitud);
            return modelMapper.map(tiposSolicitudRepository.save(tipoSolicitudGuardar), TiposSolicitudDTO.class);
        } else {
            throw new NotDataFound(NOEXISTENDATOS);
        }
    }


    @Override
    public String delete(Long id, String usuarios) {
        Optional<TiposSolicitud> tiposSolicitudOptional = Optional.ofNullable(tiposSolicitudRepository.findById(id)
                .orElseThrow(() -> new NotDataFound("No existe el Tipo solicitud: " + id)));
        if (tiposSolicitudOptional.isPresent()) {
            tiposSolicitudRepository.deleteById(id);
            LogsTiposSolicitud logsTiposSolicitud = LogsTiposSolicitud.builder()
                    .usuario(usuarios)
                    .acciones(Acciones.DELETE)
                    .tiposSolicitud(tiposSolicitudOptional.get().getTipoSolicitud())
                    .fechalog(new Date()).build();
            logsTiposSolicitudRepository.save(logsTiposSolicitud);
            return modelMapper.map(tiposSolicitudOptional.get(), TiposSolicitudDTO.class).getTipoSolicitud() + "Tipo solicitud eliminado con Exito";
        } else {
            throw new NotDataFound(NOEXISTENDATOS);
        }

    }
}
