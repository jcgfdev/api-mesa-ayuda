package com.enyoi.apimesaayuda.aplicacion.controller;

import com.enyoi.apimesaayuda.aplicacion.dtos.DependenciasDTO;
import com.enyoi.apimesaayuda.aplicacion.dtos.EstadosSolicitudDTO;
import com.enyoi.apimesaayuda.aplicacion.entities.Dependencias;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.DependenciasRequests;
import com.enyoi.apimesaayuda.aplicacion.services.IDependenciasService;
import com.enyoi.apimesaayuda.base.utils.ResponseDTOService;
import com.enyoi.apimesaayuda.security.dtos.UsuariosDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "data found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EstadosSolicitudDTO.class))}),
            @ApiResponse(responseCode = "401", description = "debe iniciar session",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "error al solicitar informacion",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/obtener-todos")
    public ResponseEntity<List<DependenciasDTO>> obtenerTodos() {
        return (ResponseEntity<List<DependenciasDTO>>) responseDTOService.response(dependenciasService.findAll(), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "data found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EstadosSolicitudDTO.class))}),
            @ApiResponse(responseCode = "401", description = "debe iniciar session",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "error al solicitar informacion",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/obtener-uno")
    public ResponseEntity<DependenciasDTO> obtenerUno(@RequestParam("id") long id) {
        return (ResponseEntity<DependenciasDTO>) responseDTOService.response(dependenciasService.findById(id), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "data found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EstadosSolicitudDTO.class))}),
            @ApiResponse(responseCode = "401", description = "debe iniciar session",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "error al solicitar informacion",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/obtener-uno-por-nombre")
    public ResponseEntity<DependenciasDTO> obtenerUno(@RequestParam("nombre") String nombre) {
        return (ResponseEntity<DependenciasDTO>) responseDTOService.response(dependenciasService.findByNombreDependencia(nombre), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dependencia creada exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuariosDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Debe iniciar sesion para crear la Dependencia",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al crear Dependencia",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO')")
    @PostMapping("/saveDependencia")
    public ResponseEntity<DependenciasDTO> saveUser(@Valid @RequestBody DependenciasRequests DependenciasRequests, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return (ResponseEntity<DependenciasDTO>) responseDTOService.response(HttpStatus.BAD_REQUEST);
        } else {
            return (ResponseEntity<DependenciasDTO>) responseDTOService.response(dependenciasService.create(DependenciasRequests.getNombreDependencia()), HttpStatus.CREATED);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dependencias actualizada exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuariosDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Debe iniciar sesion para actualizar",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al actualizar",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO')")
    @PutMapping("/update-dependencia")
    public ResponseEntity<DependenciasDTO> updateDependencia(@Valid @RequestBody DependenciasRequests dependenciasRequests, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return (ResponseEntity<DependenciasDTO>) responseDTOService.response(HttpStatus.BAD_REQUEST);
        } else {
            return (ResponseEntity<DependenciasDTO>) responseDTOService.response(dependenciasService.update(dependenciasRequests), HttpStatus.OK);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dependencias eliminada exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuariosDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Debe iniciar sesion para eliminar",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al eliminar",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/eliminar-uno")
    public ResponseEntity<String> eliminarUno(@RequestParam("id") long id) {
        return (ResponseEntity<String>) responseDTOService.response(dependenciasService.delete(id), HttpStatus.OK);
    }
}
