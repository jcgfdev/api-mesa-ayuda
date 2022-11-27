package com.enyoi.apimesaayuda.aplicacion.services.impl;

import com.enyoi.apimesaayuda.aplicacion.dtos.DependenciasDTO;
import com.enyoi.apimesaayuda.aplicacion.entities.Dependencias;
import com.enyoi.apimesaayuda.aplicacion.entities.logs.LogsDependencias;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.CrearDependenciasRequest;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.DependenciasRequests;
import com.enyoi.apimesaayuda.aplicacion.repositories.DependenciasRepository;
import com.enyoi.apimesaayuda.aplicacion.repositories.logs.LogsDependenciasRepository;
import com.enyoi.apimesaayuda.aplicacion.services.IDependenciasService;
import com.enyoi.apimesaayuda.base.enums.Acciones;
import com.enyoi.apimesaayuda.base.exceptions.AlreadyExists;
import com.enyoi.apimesaayuda.base.exceptions.NotDataFound;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DependenciasService implements IDependenciasService {
    //Utilidades
    private final ModelMapper modelMapper;
    private final LogsDependenciasRepository logsDependenciasRepository;
    private static final String NOEXISTENDATOS = "No existen datos";

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
            return modelMapper.map(dependencias, DependenciasDTO.class);
        } else {
            throw new NotDataFound(NOEXISTENDATOS);
        }
    }

    @Override
    public DependenciasDTO findByNombreDependencia(String nombreDependencia) {
        Optional<Dependencias> dependenciasOptional = dependenciasRepository.findByNombreDependencia(nombreDependencia);
        Dependencias dependencias;
        if (dependenciasOptional.isPresent()) {
            dependencias = dependenciasOptional.get();
            return modelMapper.map(dependencias, DependenciasDTO.class);
        } else {
            throw new NotDataFound(NOEXISTENDATOS);
        }
    }

    @Override
    public DependenciasDTO create(CrearDependenciasRequest crearDependenciasRequest) {
        Optional<Dependencias> dependenciasOptional = dependenciasRepository.findByNombreDependencia(crearDependenciasRequest.getNombreDependencia());
        if (dependenciasOptional.isPresent()) {
            throw new AlreadyExists("dependencia ya existe");
        } else {
            Dependencias dependencias = new Dependencias();
            dependencias.setNombreDependencia(crearDependenciasRequest.getNombreDependencia());
            LogsDependencias logsDependencias = LogsDependencias.builder()
                    .usuario(crearDependenciasRequest.getUsuario())
                    .acciones(Acciones.CREATED)
                    .dependencias(dependencias.getNombreDependencia())
                    .fechalog(new Date()).build();
            logsDependenciasRepository.save(logsDependencias);
            return modelMapper.map(dependenciasRepository.save(dependencias), DependenciasDTO.class);
        }
    }

    @Override
    public DependenciasDTO update(DependenciasRequests dependenciasRequests) {
        Optional<Dependencias> dependenciasOptional = dependenciasRepository.findById(dependenciasRequests.getId());
        if (dependenciasOptional.isPresent()) {
            Dependencias dependeciaGuardar = dependenciasOptional.get();
            dependeciaGuardar.setNombreDependencia(dependenciasRequests.getNombreDependencia());
            dependeciaGuardar = dependenciasRepository.save(dependeciaGuardar);
            LogsDependencias logsDependencias = LogsDependencias.builder()
                    .usuario(dependenciasRequests.getUsuario())
                    .acciones(Acciones.UPDATE)
                    .dependencias(dependenciasRequests.getNombreDependencia())
                    .fechalog(new Date()).build();
            logsDependenciasRepository.save(logsDependencias);
            return modelMapper.map(dependeciaGuardar, DependenciasDTO.class);
        } else {
            throw new NotDataFound(NOEXISTENDATOS);
        }
    }

    @Override
    public String delete(Long id, String usuarios) {
        Dependencias dependenciasOptional = dependenciasRepository.findById(id)
                .orElseThrow(() -> new NotDataFound(NOEXISTENDATOS));
        dependenciasRepository.delete(dependenciasOptional);
            LogsDependencias logsDependencias = LogsDependencias.builder()
                    .usuario(usuarios)
                    .acciones(Acciones.DELETE)
                    .dependencias(dependenciasOptional.getNombreDependencia())
                    .fechalog(new Date()).build();
            logsDependenciasRepository.save(logsDependencias);
            return dependenciasOptional.getNombreDependencia() + "dato no encontrado";
    }
}