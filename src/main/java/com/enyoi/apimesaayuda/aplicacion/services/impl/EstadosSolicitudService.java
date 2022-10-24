package com.enyoi.apimesaayuda.aplicacion.services.impl;
import com.enyoi.apimesaayuda.aplicacion.dtos.EstadosSolicitudDTO;
import com.enyoi.apimesaayuda.aplicacion.entities.EstadosSolicitud;
import com.enyoi.apimesaayuda.aplicacion.repositories.EstadosSolicitudRepository;
import com.enyoi.apimesaayuda.aplicacion.services.IEstadosSolicitudService;
import com.enyoi.apimesaayuda.base.exceptions.NotDataFound;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstadosSolicitudService implements IEstadosSolicitudService {

    private final ModelMapper modelMapper;

    private final EstadosSolicitudRepository estadosSolicitudRepository;

    @Override
    public List<EstadosSolicitudDTO> findAll() {
        List<EstadosSolicitud> estadosSolicitudList = estadosSolicitudRepository.findAll();
        List<EstadosSolicitudDTO> estadosSolicitudDTOList = new ArrayList<>();
        if (estadosSolicitudList.size() > 0) {
            for (int i = 0; i <= estadosSolicitudList.size(); i++) {
                EstadosSolicitudDTO estadosSolicitudDTO = modelMapper.map(estadosSolicitudList.get(i), EstadosSolicitudDTO.class);
                estadosSolicitudDTOList.add(estadosSolicitudDTO);
            }
        }
        return estadosSolicitudDTOList;
    }

    @Override
    public EstadosSolicitudDTO findById(Long id) {
        Optional<EstadosSolicitud> estadosSolicitudOptional = estadosSolicitudRepository.findById(id);
        EstadosSolicitud estadosSolicitud;
        if (estadosSolicitudOptional.isPresent()) {
            estadosSolicitud = estadosSolicitudOptional.get();
            EstadosSolicitudDTO estadosSolicitudDTO = modelMapper.map(estadosSolicitud, EstadosSolicitudDTO.class);
            return estadosSolicitudDTO;
        } else {
            throw new NotDataFound("el nombre de solicitud no existe");
        }
    }

    @Override
    public EstadosSolicitudDTO findByNombreEstado(String nombreEstado) {
        Optional<EstadosSolicitud> estadosSolicitudOptional = estadosSolicitudRepository.findByNombreEstado(nombreEstado);
        EstadosSolicitud estadosSolicitud;
        if (estadosSolicitudOptional.isPresent()) {
            estadosSolicitud = estadosSolicitudOptional.get();
            EstadosSolicitudDTO estadosSolicitudDTO = modelMapper.map(estadosSolicitud, EstadosSolicitudDTO.class);
            return estadosSolicitudDTO;
        } else {
            throw new NotDataFound("el nombre solicitud no existe");
        }
    }

    @Override
    public EstadosSolicitudDTO create(String nombreEstado) {
        Optional<EstadosSolicitud> estadosSolicitudOptional = estadosSolicitudRepository.findByNombreEstado(nombreEstado);

        EstadosSolicitud estadosSolicitud = new EstadosSolicitud();
        estadosSolicitud.setNombreEstado(nombreEstado);
        EstadosSolicitudDTO estadosSolicitudDTO = modelMapper.map(estadosSolicitudRepository.save(estadosSolicitud), EstadosSolicitudDTO.class);
        return estadosSolicitudDTO;


    }


    @Override
    public EstadosSolicitudDTO update(Long id, String nombreEstado) {
        Optional<EstadosSolicitud> estadosSolicitudOptional = estadosSolicitudRepository.findById(id);
        EstadosSolicitud estadosSolicitud;
        if (estadosSolicitudOptional.isPresent()) {
            estadosSolicitud = estadosSolicitudOptional.get();
            estadosSolicitud.setNombreEstado(nombreEstado);
            EstadosSolicitudDTO estadosSolicitudDTO = modelMapper.map(estadosSolicitudRepository.save(estadosSolicitud), EstadosSolicitudDTO.class);
            return estadosSolicitudDTO;


        } else {
            throw new NotDataFound("nombre solicitud no existe");
        }
    }

    @Override
    public String delete(Long id) {
        Optional<EstadosSolicitud> estadosSolicitudOptional = estadosSolicitudRepository.findById(id);
        EstadosSolicitud estadosSolicitud;
        if (estadosSolicitudOptional.isPresent()) {
            estadosSolicitud = estadosSolicitudOptional.get();
            estadosSolicitudRepository.deleteById(id);
            return null;
        } else {
            throw new NotDataFound("estado sulicitud no existe");
        }
    }
}