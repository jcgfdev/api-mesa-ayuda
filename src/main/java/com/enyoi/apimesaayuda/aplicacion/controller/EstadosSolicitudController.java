package com.enyoi.apimesaayuda.aplicacion.controller;
import com.enyoi.apimesaayuda.aplicacion.dtos.EstadosSolicitudDTO;
import com.enyoi.apimesaayuda.aplicacion.entities.EstadosSolicitud;
import com.enyoi.apimesaayuda.aplicacion.repositories.EstadosSolicitudRepository;
import com.enyoi.apimesaayuda.aplicacion.services.IEstadosSolicitudService;
import com.enyoi.apimesaayuda.base.exceptions.ResourceNotFoundException;
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
@RequestMapping("/estadosSolicitud")
@RequiredArgsConstructor
public class EstadosSolicitudController {

    private final ResponseDTOService responseDTOService;
    private final IEstadosSolicitudService estadosSolicitudService;
    private final EstadosSolicitudRepository estadosSolicitudRepository;
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/obtener-todos-estados")
    public ResponseEntity<List<EstadosSolicitudDTO>> obtenerTodos() {
        return (ResponseEntity<List<EstadosSolicitudDTO>>) responseDTOService.response(estadosSolicitudService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/obtener-todos-estados/{id}")
    public ResponseEntity<?> obtenerId(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        estadosSolicitudService.findById(id);
        response.put("mensaje", "Solicitud Encontrada!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/obtener-nombre-estados/{nombreEstado}")
    public ResponseEntity<?> obtenerNombreEstado(@PathVariable String nombreEstado) {
        Map<String, Object> response = new HashMap<>();
        estadosSolicitudService.findByNombreEstado(nombreEstado);
        response.put("mensaje", "Solicitd de nombre encontrada");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @PostMapping("/crearSolicitud")
    private ResponseEntity<EstadosSolicitudDTO> crearSolicitud(@RequestBody EstadosSolicitud estadosSolicitud){

         return (ResponseEntity<EstadosSolicitudDTO>)responseDTOService.response(estadosSolicitudService.create(estadosSolicitud.getNombreEstado()), HttpStatus.CREATED);

    }


    @PutMapping("/update-estado/{id}")
    public ResponseEntity<EstadosSolicitudDTO> update(@PathVariable Long id, @RequestBody EstadosSolicitud estadosSolicitud) {
    EstadosSolicitud upestado = estadosSolicitudRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("El estado no existe: " + id));
    upestado.setNombreEstado(estadosSolicitud.getNombreEstado());
    estadosSolicitudRepository.save(upestado);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete-estado/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable ("id") Long id) {
       EstadosSolicitud estadosSolicitud = estadosSolicitudRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));
       estadosSolicitudRepository.delete(estadosSolicitud);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
