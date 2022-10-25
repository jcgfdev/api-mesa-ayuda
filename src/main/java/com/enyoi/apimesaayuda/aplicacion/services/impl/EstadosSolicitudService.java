package com.enyoi.apimesaayuda.aplicacion.services.impl;
import com.enyoi.apimesaayuda.aplicacion.dtos.DependenciasDTO;
import com.enyoi.apimesaayuda.aplicacion.dtos.EstadosSolicitudDTO;
import com.enyoi.apimesaayuda.aplicacion.entities.EstadosSolicitud;
import com.enyoi.apimesaayuda.aplicacion.repositories.EstadosSolicitudRepository;
import com.enyoi.apimesaayuda.aplicacion.services.IEstadosSolicitudService;
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
     List<EstadosSolicitud> estadosSolicitudList = estadosSolicitudRepository.findByNombreEstado(nombreEstado);
List<EstadosSolicitudDTO> estadosSolicitudDTOList = new ArrayList<>();
    estadosSolicitudList.forEach(estadosSolicitud -> {
        EstadosSolicitudDTO estadosSolicitudDTO = modelMapper.map(estadosSolicitud, EstadosSolicitudDTO.class);
        estadosSolicitudDTOList.add(estadosSolicitudDTO);
    });
return (EstadosSolicitudDTO) estadosSolicitudDTOList;
    }

    @Override
    public EstadosSolicitudDTO create(String nombreEstado) {
        return null;
    }


    public EstadosSolicitudDTO update(Long id, String nombreEstado) {
        return null;
    }




    @Transactional(rollbackFor = Exception.class)
    public EstadosSolicitudDTO createNew(String nombreEstado, Long id) {
        Optional<EstadosSolicitud> estadosSolicitudOptional = estadosSolicitudRepository.findById(id);
        if (estadosSolicitudOptional.isPresent()) {
            throw new AlreadyExists("dependencia ya Fue creada");
        } else {
            EstadosSolicitud estadosSolicitud = new EstadosSolicitud();
            estadosSolicitud.setNombreEstado(nombreEstado);
            EstadosSolicitudDTO estadosSolicitudDTO = modelMapper.map(estadosSolicitudRepository.save(estadosSolicitud), EstadosSolicitudDTO.class);
            return estadosSolicitudDTO;
        }
    }



    public EstadosSolicitudDTO updateId(EstadosSolicitud estadosSolicitud) {
        Optional<EstadosSolicitud> estadosSolicitudOptional = estadosSolicitudRepository.findById(estadosSolicitud.getId());
        if (estadosSolicitudOptional.isPresent()) {
            EstadosSolicitud updateEstado = estadosSolicitudOptional.get();
            updateEstado.setNombreEstado(estadosSolicitud.getNombreEstado());

            updateEstado = estadosSolicitudRepository.save(updateEstado);

            return modelMapper.map(updateEstado,EstadosSolicitudDTO.class);

        } else {
            throw new NotDataFound("Solicitud no existe");
        }
    }
    @Override
    public String delete(Long id) {
        estadosSolicitudRepository.deleteById(id);
        return "Eliminado con Exito";
    }

}