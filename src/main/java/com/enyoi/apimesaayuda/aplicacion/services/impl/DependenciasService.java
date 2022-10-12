package com.enyoi.apimesaayuda.aplicacion.services.impl;

import com.enyoi.apimesaayuda.aplicacion.dtos.DependenciasDTO;
import com.enyoi.apimesaayuda.aplicacion.entities.Dependencias;
import com.enyoi.apimesaayuda.aplicacion.repositories.DependenciasRepository;
import com.enyoi.apimesaayuda.aplicacion.services.IDependenciasService;
import com.enyoi.apimesaayuda.base.exceptions.NotDataFound;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
        for (int i = 0; i <= dependenciasList.size(); i++) {
            DependenciasDTO dependenciasDTO = modelMapper.map(dependenciasList.get(i), DependenciasDTO.class);
            dependenciasDTOList.add(dependenciasDTO);
        }
        return dependenciasDTOList;
    }

    @Override
    public DependenciasDTO findById(Long id) {
        Optional<Dependencias> dependenciasOptional = dependenciasRepository.findById(id);
        Dependencias dependencias;
        if (dependenciasOptional.isPresent()) {
            dependencias = dependenciasOptional.get();
            DependenciasDTO dependenciasDTO = modelMapper.map(dependencias, DependenciasDTO.class);
            return  dependenciasDTO;
        } else {
            throw new NotDataFound("dependencia no existe");
        }
    }

    @Override
    public DependenciasDTO findByNombreDependencia(String nombreDependencia) {
        return null;
    }

    @Override
    public DependenciasDTO create(String nombreDependencia) {
        return null;
    }

    @Override
    public DependenciasDTO update(Long id, String nombreDependencia) {
        return null;
    }

    @Override
    public String delete(Long id) {
        return null;
    }
}
