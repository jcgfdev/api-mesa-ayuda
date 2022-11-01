package com.enyoi.apimesaayuda.aplicacion.controller;

import com.enyoi.apimesaayuda.aplicacion.dtos.EstadosSolicitudDTO;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.ActualizarEstadosSolicitudRequests;
import com.enyoi.apimesaayuda.aplicacion.services.IEstadosSolicitudService;
import com.enyoi.apimesaayuda.base.utils.ResponseDTOService;

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
@RequestMapping("/estadosSolicitud")
@RequiredArgsConstructor
public class EstadosSolicitudController {

    private final ResponseDTOService responseDTOService;
    private final IEstadosSolicitudService estadosSolicitudService;

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
    @GetMapping("/obtener-todos-estados")
    public ResponseEntity<List<EstadosSolicitudDTO>> obtenerTodos() {
        return (ResponseEntity<List<EstadosSolicitudDTO>>) responseDTOService.response(estadosSolicitudService.findAll(), HttpStatus.OK);
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
    @GetMapping("/obtener-todos-id")
    public ResponseEntity<EstadosSolicitudDTO> obtenerId(@RequestParam("id") long id) {
        return (ResponseEntity<EstadosSolicitudDTO>) responseDTOService.response(estadosSolicitudService.findById(id), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "data found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EstadosSolicitudDTO.class))}),
            @ApiResponse(responseCode = "401", description = "debe iniciar session",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "error al solicitar",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/obtener-nombre-estados")
    public ResponseEntity<EstadosSolicitudDTO> obtenerNombreEstado(@PathVariable("nombreEstado") String nombreEstado) {
        return (ResponseEntity<EstadosSolicitudDTO>) responseDTOService.response(estadosSolicitudService.findByNombreEstado(nombreEstado), HttpStatus.OK);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "data found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EstadosSolicitudDTO.class))}),
            @ApiResponse(responseCode = "401", description = "debe iniciar session",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "error al solicitar",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @PostMapping("/crearSolicitud")
    public ResponseEntity<EstadosSolicitudDTO> create(@RequestParam("nombreEstado")String nombreEstado) {
        return (ResponseEntity<EstadosSolicitudDTO>) responseDTOService.response(estadosSolicitudService.create(nombreEstado), HttpStatus.CREATED);
    }



    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "data found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EstadosSolicitudDTO.class))}),
            @ApiResponse(responseCode = "401", description = "debe iniciar session",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "error al solicitar",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @PutMapping("/update-estado")
    public ResponseEntity<EstadosSolicitudDTO> update(@Valid @RequestBody ActualizarEstadosSolicitudRequests actualizarEstadosSolicitudRequests, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return (ResponseEntity<EstadosSolicitudDTO>) responseDTOService.response(HttpStatus.BAD_REQUEST);
        } else {
            return (ResponseEntity<EstadosSolicitudDTO>) responseDTOService.response(estadosSolicitudService.update(actualizarEstadosSolicitudRequests), HttpStatus.OK);
        }
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "data found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EstadosSolicitudDTO.class))}),
            @ApiResponse(responseCode = "401", description = "debe iniciar session",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "error al solicitar",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete-estado")
    public ResponseEntity<String> delete(@RequestParam("id") long id){
        return (ResponseEntity<String>)responseDTOService.response(estadosSolicitudService.delete(id), HttpStatus.OK);

    }


}
