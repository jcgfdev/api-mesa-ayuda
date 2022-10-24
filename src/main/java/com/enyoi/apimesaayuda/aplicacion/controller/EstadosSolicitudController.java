package com.enyoi.apimesaayuda.aplicacion.controller;
import com.enyoi.apimesaayuda.aplicacion.dtos.EstadosSolicitudDTO;
import com.enyoi.apimesaayuda.aplicacion.services.IEstadosSolicitudService;
import com.enyoi.apimesaayuda.base.utils.ResponseDTOService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/estadossolicitud")
@RequiredArgsConstructor
public class EstadosSolicitudController {
    private final ResponseDTOService responseDTOService;

    private IEstadosSolicitudService estadosSolicitudService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/obtener-todos-estados")
    public ResponseEntity<List<EstadosSolicitudDTO>> obtenerTodos(){
        return (ResponseEntity<List<EstadosSolicitudDTO>>) responseDTOService.response(estadosSolicitudService.findAll(), HttpStatus.OK);

    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/obtener-todos-estados/{id}")
    public ResponseEntity<?> obtenerId(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        estadosSolicitudService.findById(id);
        response.put("mensaje", "Solicitud Encontrada!");
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }



}
