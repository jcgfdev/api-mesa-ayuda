package com.enyoi.apimesaayuda.aplicacion.services.impl;

import com.enyoi.apimesaayuda.aplicacion.dtos.TiposSolicitudDTO;
import com.enyoi.apimesaayuda.aplicacion.entities.TiposSolicitud;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.TiposSolicitudRequest;
import com.enyoi.apimesaayuda.aplicacion.repositories.TiposSolicitudRepository;
import com.enyoi.apimesaayuda.aplicacion.services.ITiposSolicitudService;
import com.enyoi.apimesaayuda.base.exceptions.AlreadyExists;
import com.enyoi.apimesaayuda.base.exceptions.NotDataFound;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TiposSolicitudService implements ITiposSolicitudService {
    private final ModelMapper modelMapper;
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
            TiposSolicitudDTO tiposSolicitudDTO = modelMapper.map(tiposSolicitud, TiposSolicitudDTO.class);
            return tiposSolicitudDTO;
        } else {
            throw new NotDataFound("Tipo solicitud no existe");
        }

    }

    @Override
    public TiposSolicitudDTO findByTipoSolicitud(String tipoSolicitud) {
        Optional<TiposSolicitud> tiposSolicitudOptional = tiposSolicitudRepository.findByTipoSolicitud(tipoSolicitud);
        TiposSolicitud tiposSolicitud;
        if (tiposSolicitudOptional.isPresent()) {
            tiposSolicitud = tiposSolicitudOptional.get();
            TiposSolicitudDTO tiposSolicitudDTO = modelMapper.map(tiposSolicitud, TiposSolicitudDTO.class);
            return tiposSolicitudDTO;
        } else {
            throw new NotDataFound("Tipo solicitud no existe");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TiposSolicitudDTO create(String tipoSolicitud) {
        Optional<TiposSolicitud> tiposSolicitudOptional = tiposSolicitudRepository.findByTipoSolicitud(tipoSolicitud);
        if (tiposSolicitudOptional.isPresent()) {
            throw new AlreadyExists("Tipo solicitud ya existe");
        } else {
            TiposSolicitud tiposSolicitud = new TiposSolicitud();
            tiposSolicitud.setTipoSolicitud(tipoSolicitud);
            TiposSolicitudDTO tiposSolicitudDTO = modelMapper.map(tiposSolicitudRepository.save(tiposSolicitud), TiposSolicitudDTO.class);
            return tiposSolicitudDTO;
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
            TiposSolicitudDTO tiposSolicitudDTO = modelMapper.map(tiposSolicitudRepository.save(tipoSolicitudGuardar), TiposSolicitudDTO.class);
            return tiposSolicitudDTO;
        } else {
            throw new NotDataFound("Tipo solicitud no existe");
        }
    }


    @Override
    public String delete(Long id) {
        Optional<TiposSolicitud> tiposSolicitudOptional = Optional.ofNullable(tiposSolicitudRepository.findById(id)
                .orElseThrow(() -> new NotDataFound("No existe el Tipo solicitud: " + id)));
        tiposSolicitudRepository.deleteById(id);
        return tiposSolicitudOptional.get() + "Tipo solicitud eliminado con Exito";
    }
}
