package com.enyoi.apimesaayuda.aplicacion.controller;
import com.enyoi.apimesaayuda.aplicacion.dtos.DependenciasDTO;
import com.enyoi.apimesaayuda.aplicacion.dtos.EstadosSolicitudDTO;
import com.enyoi.apimesaayuda.aplicacion.entities.Dependencias;
import com.enyoi.apimesaayuda.aplicacion.entities.EstadosSolicitud;
import com.enyoi.apimesaayuda.aplicacion.repositories.EstadosSolicitudRepository;
import com.enyoi.apimesaayuda.aplicacion.services.IEstadosSolicitudService;
import com.enyoi.apimesaayuda.base.exceptions.ResourceNotFoundException;
import com.enyoi.apimesaayuda.base.utils.ResponseDTOService;
import com.enyoi.apimesaayuda.security.dtos.UsuariosDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @GetMapping("/obtener-todos-id")
    public ResponseEntity<EstadosSolicitudDTO> obtenerId(@RequestParam("id") long id) {
        return (ResponseEntity<EstadosSolicitudDTO>) responseDTOService.response(estadosSolicitudService.findById(id), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/obtener-nombre-estados")
    public ResponseEntity<EstadosSolicitudDTO> obtenerNombreEstado(@PathVariable("nombreEstado") String nombreEstado) {
        return (ResponseEntity<EstadosSolicitudDTO>) responseDTOService.response(estadosSolicitudService.findByNombreEstado(nombreEstado), HttpStatus.OK);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estado Actualizado exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuariosDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Error al Actualizada Estado",
                    content = @Content)})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @PostMapping("/crearSolicitud")
    private ResponseEntity<EstadosSolicitudDTO> crearSolicitud(@Valid @RequestBody EstadosSolicitud estadosSolicitud, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return (ResponseEntity<EstadosSolicitudDTO>) responseDTOService.response(HttpStatus.BAD_REQUEST);
        } else {
            return (ResponseEntity<EstadosSolicitudDTO>) responseDTOService.response(estadosSolicitudService.create(estadosSolicitud.getNombreEstado()), HttpStatus.CREATED);
        }
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estado Actualizado exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuariosDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Error al Actualizada Estado",
                    content = @Content)})
    @PutMapping("/update-estado")
    public ResponseEntity<EstadosSolicitudDTO> updateId(@Valid @RequestBody EstadosSolicitud estadosSolicitud, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return (ResponseEntity<EstadosSolicitudDTO>) responseDTOService.response(HttpStatus.BAD_REQUEST);
        } else {
            return (ResponseEntity<EstadosSolicitudDTO>) responseDTOService.response(estadosSolicitudService.updateId(estadosSolicitud), HttpStatus.OK);
        }
    }
    @DeleteMapping("/delete-estado")
    public ResponseEntity<String> delete(@RequestParam("id") long id){
        return (ResponseEntity<String>)responseDTOService.response(estadosSolicitudService.delete(id), HttpStatus.OK);

    }


}
