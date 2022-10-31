package com.enyoi.apimesaayuda.aplicacion.services.impl;

import com.enyoi.apimesaayuda.aplicacion.dtos.EstadosSolicitudDTO;
import com.enyoi.apimesaayuda.aplicacion.entities.EstadosSolicitud;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.ActualizarEstadosSolicitudRequests;
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

        estadosSolicitudList.forEach(estadosSolicitud -> {
            EstadosSolicitudDTO estadosSolicitudDTO = modelMapper.map(estadosSolicitud, EstadosSolicitudDTO.class);
            estadosSolicitudDTOList.add(estadosSolicitudDTO);
        });

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
            throw new NotDataFound("estado no existe");
        }
    }

    @Override
    public EstadosSolicitudDTO findByNombreEstado(String nombreEstado) {
        Optional<EstadosSolicitud> estadosSolicitudOptional = estadosSolicitudRepository.findByNombreEstado(nombreEstado);
        EstadosSolicitud estadosSolicitud;
        if(estadosSolicitudOptional.isPresent()){
            estadosSolicitud = estadosSolicitudOptional.get();
            EstadosSolicitudDTO estadosSolicitudDTO = modelMapper.map(estadosSolicitud, EstadosSolicitudDTO.class);
            return estadosSolicitudDTO;
        }else {
            throw new NotDataFound("estado no existe ");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EstadosSolicitudDTO create(String nombreEstado) {
        Optional<EstadosSolicitud> estadosSolicitudOptional = estadosSolicitudRepository.findByNombreEstado(nombreEstado);
        if (estadosSolicitudOptional.isPresent()) {
            throw new AlreadyExists("nombre estado solicitud ya existe");
        } else {
            EstadosSolicitud estadosSolicitud = new EstadosSolicitud();
            estadosSolicitud.setNombreEstado(nombreEstado);
            EstadosSolicitudDTO estadosSolicitudDTO = modelMapper.map(estadosSolicitudRepository.save(estadosSolicitud), EstadosSolicitudDTO.class);
            return estadosSolicitudDTO;
        }
    }



    @Override
    public EstadosSolicitudDTO update (ActualizarEstadosSolicitudRequests actualizarEstadosSolicitudRequests){
        Optional<EstadosSolicitud> estadosSolicitudOptional = estadosSolicitudRepository.findById(actualizarEstadosSolicitudRequests.getEstadosSolicitudId());
        if (estadosSolicitudOptional.isPresent()) {
            EstadosSolicitud updateEstado = estadosSolicitudOptional.get();
            updateEstado.setNombreEstado(actualizarEstadosSolicitudRequests.getNombreEstado() != null
                    ? actualizarEstadosSolicitudRequests.getNombreEstado()
                    : updateEstado.getNombreEstado());
            EstadosSolicitudDTO estadosSolicitudDTO = modelMapper.map(estadosSolicitudRepository.save(updateEstado), EstadosSolicitudDTO.class);


            return estadosSolicitudDTO;
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