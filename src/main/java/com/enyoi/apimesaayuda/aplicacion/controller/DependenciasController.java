package com.enyoi.apimesaayuda.aplicacion.controller;

import com.enyoi.apimesaayuda.aplicacion.dtos.DependenciasDTO;
import com.enyoi.apimesaayuda.aplicacion.dtos.EstadosSolicitudDTO;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.CrearDependenciasRequest;
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
    @GetMapping("/obtenerTodos")
    public ResponseEntity<List<DependenciasDTO>> obtenerTodos(@RequestParam("usuary") String user) {
        return (ResponseEntity<List<DependenciasDTO>>) responseDTOService.response(dependenciasService.findAll(user), HttpStatus.OK);
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO')")
    @GetMapping("/obtenerUno")
    public ResponseEntity<DependenciasDTO> obtenerUnoId(@RequestParam("id") long id,
                                                        @RequestParam("usuary") String user) {
        return (ResponseEntity<DependenciasDTO>) responseDTOService.response(dependenciasService.findById(id, user), HttpStatus.OK);
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
    @GetMapping("/obtenerUnoporNombre")
    public ResponseEntity<DependenciasDTO> obtenerporNombre(@RequestParam("nombre") String nombre,
                                                            @RequestParam("user") String user) {
        return (ResponseEntity<DependenciasDTO>) responseDTOService.response(dependenciasService.findByNombreDependencia(nombre, user), HttpStatus.OK);
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/saveDependencia")
    public ResponseEntity<DependenciasDTO> create(@Valid @RequestBody CrearDependenciasRequest crearDependenciasRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return (ResponseEntity<DependenciasDTO>) responseDTOService.response(HttpStatus.BAD_REQUEST);
        } else {
            return (ResponseEntity<DependenciasDTO>) responseDTOService.response(dependenciasService.create(crearDependenciasRequest), HttpStatus.CREATED);
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/updateDependencia")
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
    @DeleteMapping("/eliminarUno")
    public ResponseEntity<String> eliminarUno(@RequestParam("id") long id,
                                              @RequestParam("usuario") String usuarios) {
        return (ResponseEntity<String>) responseDTOService.response(dependenciasService.delete(id, usuarios), HttpStatus.OK);
    }
}
