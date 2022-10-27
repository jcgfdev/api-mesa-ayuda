package com.enyoi.apimesaayuda.aplicacion.services.impl;

import com.enyoi.apimesaayuda.aplicacion.dtos.TiposSolicitudDTO;
import com.enyoi.apimesaayuda.aplicacion.entities.TiposSolicitud;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.TiposSolicitudRequests;
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
    public List<TiposSolicitudDTO> finAll() {
        List<TiposSolicitud> tiposSolicitudList = tiposSolicitudRepository.findAll();
        List<TiposSolicitudDTO> tiposSolicitudDTOList = new ArrayList<>();
        if (tiposSolicitudList.size() > 0) {
            for (int i = 0; i <= tiposSolicitudList.size(); i++) {
                TiposSolicitudDTO tiposSolicitudDTO = modelMapper.map(tiposSolicitudDTOList.get(i), TiposSolicitudDTO.class);
                tiposSolicitudDTOList.add(tiposSolicitudDTO);
            }
        }
        return tiposSolicitudDTOList;
    }

    @Override
    public List<TiposSolicitudDTO> findAll() {
        return null;
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
            throw new NotDataFound("tipo_solicitud no existe");
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
            throw new NotDataFound("tipo_solicitud no existe");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TiposSolicitudDTO create(String tipoSolicitud) {
        Optional<TiposSolicitud> tiposSolicitudOptional = tiposSolicitudRepository.findByTipoSolicitud(tipoSolicitud);
        if (tiposSolicitudOptional.isPresent()) {
            throw new AlreadyExists("tipo_solicitud ya existe");
        } else {
            TiposSolicitud tiposSolicitud = new TiposSolicitud();
            tiposSolicitud.setTipoSolicitud(tipoSolicitud);
            TiposSolicitudDTO tiposSolicitudDTO = modelMapper.map(tiposSolicitudRepository.save(tiposSolicitud), TiposSolicitudDTO.class);
            return tiposSolicitudDTO;
        }

    }

    @Override
    public TiposSolicitudDTO update(TiposSolicitudRequests tiposSolicitudRequests) {
        return null;
    }


    @Override
    public String delete(Long id) {
        return null;
    }
}
