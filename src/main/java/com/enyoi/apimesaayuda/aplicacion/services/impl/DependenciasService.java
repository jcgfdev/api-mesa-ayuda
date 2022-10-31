package com.enyoi.apimesaayuda.aplicacion.services.impl;

import com.enyoi.apimesaayuda.aplicacion.dtos.DependenciasDTO;
import com.enyoi.apimesaayuda.aplicacion.entities.Dependencias;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.DependenciasRequests;
import com.enyoi.apimesaayuda.aplicacion.repositories.DependenciasRepository;
import com.enyoi.apimesaayuda.aplicacion.services.IDependenciasService;
import com.enyoi.apimesaayuda.base.exceptions.AlreadyExists;
import com.enyoi.apimesaayuda.base.exceptions.NotDataFound;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DependenciasService implements IDependenciasService {
    //Utilidades
    private final ModelMapper modelMapper;

    //Repositorios
    private final DependenciasRepository dependenciasRepository;

    @Override
    public List<DependenciasDTO> findAll() {
        List<Dependencias> dependenciasList = dependenciasRepository.findAll();
        List<DependenciasDTO> dependenciasDTOList = new ArrayList<>();

            dependenciasList.forEach(dependencia -> {
                DependenciasDTO dependenciasDTO = modelMapper.map(dependencia, DependenciasDTO.class);
                dependenciasDTOList.add(dependenciasDTO);
            });

        return dependenciasDTOList;
    }

    @Override
    public DependenciasDTO findById(Long id) {
        Optional<Dependencias> dependenciasOptional = dependenciasRepository.findById(id);
        Dependencias dependencias;
        if (dependenciasOptional.isPresent()) {
            dependencias = dependenciasOptional.get();
            DependenciasDTO dependenciasDTO = modelMapper.map(dependencias, DependenciasDTO.class);
            return dependenciasDTO;
        } else {
            throw new NotDataFound("dependencia no existe");
        }
    }

    @Override
    public DependenciasDTO findByNombreDependencia(String nombreDependencia) {
        Optional<Dependencias> dependenciasOptional = dependenciasRepository.findByNombreDependencia(nombreDependencia);
        Dependencias dependencias;
        if (dependenciasOptional.isPresent()) {
            dependencias = dependenciasOptional.get();
            DependenciasDTO dependenciasDTO = modelMapper.map(dependencias, DependenciasDTO.class);
            return dependenciasDTO;
        } else {
            throw new NotDataFound("dependencia no existe");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DependenciasDTO create(String nombreDependencia) {
        Optional<Dependencias> dependenciasOptional = dependenciasRepository.findByNombreDependencia(nombreDependencia);
        if (dependenciasOptional.isPresent()) {
            throw new AlreadyExists("dependencia ya existe");
        } else {
            Dependencias dependencias = new Dependencias();
            dependencias.setNombreDependencia(nombreDependencia);
            DependenciasDTO dependenciasDTO = modelMapper.map(dependenciasRepository.save(dependencias), DependenciasDTO.class);
            return dependenciasDTO;
        }
    }

    @Override
    public DependenciasDTO update(DependenciasRequests dependenciasRequests) {
        Optional<Dependencias> dependenciasOptional = dependenciasRepository.findById(dependenciasRequests.getId());
        if (dependenciasOptional.isPresent()) {
            Dependencias dependeciaGuardar = dependenciasOptional.get();
            dependeciaGuardar.setNombreDependencia(dependenciasRequests.getNombreDependencia());
            //dependeciaGuardar.setApellido(dependencias.getApellido());

            dependeciaGuardar = dependenciasRepository.save(dependeciaGuardar);

            return modelMapper.map(dependeciaGuardar,DependenciasDTO.class);

        } else {
            throw new NotDataFound("dependencia no existe");
        }
    }

    @Override
    public String delete(Long id) {
        dependenciasRepository.deleteById(id);
        return "Registro eliminado";
    }
}
