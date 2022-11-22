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
    /*
     **@Auth: D David Galeano Puche
     */
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

/*
**@Auth: D David Galeano Puche
 */
    @Override
    public EstadosSolicitudDTO findById(Long id) {
        Optional<EstadosSolicitud> estadosSolicitudOptional = estadosSolicitudRepository.findById(id);
        EstadosSolicitud estadosSolicitud;
        if (estadosSolicitudOptional.isPresent()) {
            estadosSolicitud = estadosSolicitudOptional.get();
            return modelMapper.map(estadosSolicitud, EstadosSolicitudDTO.class);
        } else {
            throw new NotDataFound("estado no existe");
        }
    }
    /*
     **@Auth: D David Galeano Puche
     */
    @Override
    public EstadosSolicitudDTO findByNombreEstado(String nombreEstado) {
        Optional<EstadosSolicitud> estadosSolicitudOptional = estadosSolicitudRepository.findByNombreEstado(nombreEstado);
        EstadosSolicitud estadosSolicitud;
        if (estadosSolicitudOptional.isPresent()){
            estadosSolicitud=estadosSolicitudOptional.get();
            return modelMapper.map(estadosSolicitud, EstadosSolicitudDTO.class);
        }
        else {
            throw new NotDataFound("Nombre estado no existe");
        }

    }


    /*
     **@Auth: D David Galeano Puche
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public EstadosSolicitudDTO create(String nombreEstado) {
        Optional<EstadosSolicitud> estadosSolicitudOptional = estadosSolicitudRepository.findByNombreEstado(nombreEstado);
        if (estadosSolicitudOptional.isPresent()) {
            throw new AlreadyExists("nombre estado solicitud ya existe. ");
        } else {
            EstadosSolicitud estadosSolicitud = new EstadosSolicitud();
            estadosSolicitud.setNombreEstado(nombreEstado);
            return modelMapper.map(estadosSolicitudRepository.save(estadosSolicitud), EstadosSolicitudDTO.class);
        }
    }
    /*
     **@Auth: D David Galeano Puche
     */
    @Override
    public EstadosSolicitudDTO update (ActualizarEstadosSolicitudRequests actualizarEstadosSolicitudRequests){
        Optional<EstadosSolicitud> estadosSolicitudOptional = estadosSolicitudRepository.findById(actualizarEstadosSolicitudRequests.getEstadosSolicitudId());
        if (estadosSolicitudOptional.isPresent()) {
            EstadosSolicitud updateEstado = estadosSolicitudOptional.get();
            updateEstado.setNombreEstado(actualizarEstadosSolicitudRequests.getNombreEstado() != null
                    ? actualizarEstadosSolicitudRequests.getNombreEstado()
                    : updateEstado.getNombreEstado());
            return modelMapper.map(estadosSolicitudRepository.save(updateEstado), EstadosSolicitudDTO.class);
        } else {
            throw new NotDataFound("Solicitud no existe");
        }
    }
    /*
     **@Auth: D David Galeano Puche
     */
    @Override
    public String delete (Long id){
        Optional<EstadosSolicitud> estadosSolicitudOptional = Optional.ofNullable(estadosSolicitudRepository.findById(id)
                .orElseThrow(() -> new NotDataFound("No existe el estado: "+ id ) ));
        if (estadosSolicitudOptional.isPresent()){
            estadosSolicitudRepository.deleteById(id);

            return modelMapper.map(estadosSolicitudOptional.get(), EstadosSolicitudDTO.class).getNombreEstado() + "Eliminado con Exito";
        }else {
            throw new NotDataFound("Estado no existe");
        }


    }
}