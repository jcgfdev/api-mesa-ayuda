package com.enyoi.apimesaayuda.aplicacion.controller;

import com.enyoi.apimesaayuda.aplicacion.dtos.DependenciasDTO;
import com.enyoi.apimesaayuda.aplicacion.services.IDependenciasService;
import com.enyoi.apimesaayuda.base.utils.ResponseDTOService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/dependencias")
@RequiredArgsConstructor
public class DependenciasController {
    private final ResponseDTOService responseDTOService;

    private final IDependenciasService dependenciasService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/obtener-todos")
    public ResponseEntity<List<DependenciasDTO>> obtenerTodos() {
        return (ResponseEntity<List<DependenciasDTO>>) responseDTOService.response(dependenciasService.findAll(), HttpStatus.OK);
    }

}
