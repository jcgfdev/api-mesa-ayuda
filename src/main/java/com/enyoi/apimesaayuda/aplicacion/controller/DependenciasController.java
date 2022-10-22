package com.enyoi.apimesaayuda.aplicacion.controller;

import com.enyoi.apimesaayuda.aplicacion.dtos.DependenciasDTO;
import com.enyoi.apimesaayuda.aplicacion.entities.Dependencias;
import com.enyoi.apimesaayuda.aplicacion.services.IDependenciasService;
import com.enyoi.apimesaayuda.base.utils.ResponseDTOService;
import com.enyoi.apimesaayuda.security.dtos.UsuariosDTO;
import com.enyoi.apimesaayuda.security.payloads.requests.UsuariosRequest;
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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dependencia creada exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuariosDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Error al crear Dependencia",
                    content = @Content)})
    @PostMapping("/saveDependencia")
    public ResponseEntity<DependenciasDTO> saveUser(@Valid @RequestBody Dependencias dependencias, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return (ResponseEntity<DependenciasDTO>) responseDTOService.response(HttpStatus.BAD_REQUEST);
        } else {
            return (ResponseEntity<DependenciasDTO>) responseDTOService.response(dependenciasService.create(dependencias.getNombreDependencia()), HttpStatus.CREATED);
        }
    }

}
