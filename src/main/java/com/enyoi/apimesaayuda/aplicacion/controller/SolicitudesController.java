package com.enyoi.apimesaayuda.aplicacion.controller;

import com.enyoi.apimesaayuda.aplicacion.dtos.SolicitudesDTO;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.ActualizarSolicitudesRequest;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.SolicitudesRequest;
import com.enyoi.apimesaayuda.aplicacion.services.ISolicitudesService;
import com.enyoi.apimesaayuda.base.utils.ResponseDTOService;
import com.enyoi.apimesaayuda.security.dtos.UsuariosDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/solicitudes")
@RequiredArgsConstructor
public class SolicitudesController {
    private final ResponseDTOService responseDTOService;
    private final ISolicitudesService solicitudesService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "data found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SolicitudesDTO.class))}),
            @ApiResponse(responseCode = "401", description = "debe iniciar session",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "error al solicitar informacion",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/findAll")
    public ResponseEntity<Page<SolicitudesDTO>> findAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                                        @RequestParam(name = "size", defaultValue = "10") int size,
                                                        @RequestParam(name = "columnFilter", defaultValue = "id") String columnFilter,
                                                        @RequestParam(name = "direction", defaultValue = "ASC") Sort.Direction direction,
                                                        @RequestParam("user")String user) {
        return (ResponseEntity<Page<SolicitudesDTO>>) responseDTOService.response(solicitudesService.findAll(page, size, columnFilter, direction, user), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "data found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SolicitudesDTO.class))}),
            @ApiResponse(responseCode = "401", description = "debe iniciar session",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "error al solicitar informacion",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/findByCodigo")
    public ResponseEntity<SolicitudesDTO> findByCodigo(@RequestParam("codigo") String codigo,
                                                       @RequestParam("user")String user) {
        return (ResponseEntity<SolicitudesDTO>) responseDTOService.response(solicitudesService.findByCodigo(codigo, user),
                HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "data found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SolicitudesDTO.class))}),
            @ApiResponse(responseCode = "401", description = "debe iniciar session",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "error al solicitar informacion",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/findByTipoSolicitudId")
    public ResponseEntity<Page<SolicitudesDTO>> findByTipoSolicitudId
            (@RequestParam("tipoSolicitudId") Long tipoSolicitudId,
             @RequestParam(name = "page", defaultValue = "0") int page,
             @RequestParam(name = "size", defaultValue = "10") int size,
             @RequestParam(name = "columFilter", defaultValue = "id") String columFilter,
             @RequestParam(name = "direction", defaultValue = "ASC") Sort.Direction direction,
             @RequestParam("user")String user) {
        return (ResponseEntity<Page<SolicitudesDTO>>) responseDTOService.response(solicitudesService
                .findByTipoSolicitudId(tipoSolicitudId, page, size, columFilter, direction, user), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "data found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SolicitudesDTO.class))}),
            @ApiResponse(responseCode = "401", description = "debe iniciar session",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "error al solicitar informacion",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/findByDependenciasId")
    public ResponseEntity<Page<SolicitudesDTO>> findByDependenciasId
            (@RequestParam("dependenciasId") Long dependenciasId,
             @RequestParam(name = "page", defaultValue = "0") int page,
             @RequestParam(name = "size", defaultValue = "10") int size,
             @RequestParam(name = "columFilter", defaultValue = "id") String columFilter,
             @RequestParam(name = "direction", defaultValue = "ASC") Sort.Direction direction,
             @RequestParam("user")String user) {
        return (ResponseEntity<Page<SolicitudesDTO>>) responseDTOService.response(solicitudesService
                .findByDependenciasId(dependenciasId, page, size, columFilter, direction, user), HttpStatus.OK);

    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "data found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SolicitudesDTO.class))}),
            @ApiResponse(responseCode = "401", description = "debe iniciar session",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "error al solicitar informacion",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/findBySolicitanteId")
    public ResponseEntity<Page<SolicitudesDTO>> findBySolicitanteId
            (@RequestParam("solicitanteId") Long solicitanteId,
             @RequestParam(name = "page", defaultValue = "0") int page,
             @RequestParam(name = "size", defaultValue = "10") int size,
             @RequestParam(name = "columFilter", defaultValue = "id") String columFilter,
             @RequestParam(name = "direction", defaultValue = "ASC") Sort.Direction direction,
             @RequestParam("user")String user) {
        return (ResponseEntity<Page<SolicitudesDTO>>) responseDTOService.response(solicitudesService
                .findBySolicitanteId(solicitanteId, page, size, columFilter, direction, user), HttpStatus.OK);

    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "data found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SolicitudesDTO.class))}),
            @ApiResponse(responseCode = "401", description = "debe iniciar session",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "error al solicitar informacion",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/findByPrioridad")
    public ResponseEntity<SolicitudesDTO> findByPrioridad(@RequestParam("prioridad") Long prioridad,
                                                       @RequestParam("user")String user) {
        return (ResponseEntity<SolicitudesDTO>) responseDTOService.response(solicitudesService.findByPrioridad(prioridad, user),
                HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "data found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SolicitudesDTO.class))}),
            @ApiResponse(responseCode = "401", description = "debe iniciar session",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "error al solicitar informacion",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/fechaSolicitud")
    public ResponseEntity<Page<SolicitudesDTO>> findByFechaSolicitudBetween(@RequestParam(value = "fechaInicio") Date fechaInicio,
                                                                            @RequestParam(value = "fechaFin") Date fechaFin,
                                                                            @RequestParam(name = "page", defaultValue = "0") int page,
                                                                            @RequestParam(name = "size", defaultValue = "10") int size,
                                                                            @RequestParam(name = "columnFilter", defaultValue = "id") String columnFilter,
                                                                            @RequestParam(name = "direction", defaultValue = "ASC") Sort.Direction direction,
                                                                            @RequestParam("user")String user) {
        return (ResponseEntity<Page<SolicitudesDTO>>) responseDTOService.response(solicitudesService.findByFechaSolicitudBetween(fechaInicio, fechaFin, page, size, columnFilter, direction, user), HttpStatus.OK);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "data found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SolicitudesDTO.class))}),
            @ApiResponse(responseCode = "401", description = "debe iniciar session",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "error al solicitar informacion",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/fechaFinalizado")
    public ResponseEntity<Page<SolicitudesDTO>> findByFechaFinalizadoBetween(@RequestParam(value = "fechaInicio") Date fechaInicio,
                                                                            @RequestParam(value = "fechaFin") Date fechaFin,
                                                                            @RequestParam(name = "page", defaultValue = "0") int page,
                                                                            @RequestParam(name = "size", defaultValue = "10") int size,
                                                                            @RequestParam(name = "columnFilter", defaultValue = "id") String columnFilter,
                                                                            @RequestParam(name = "direction", defaultValue = "ASC") Sort.Direction direction,
                                                                             @RequestParam("user")String user) {
        return (ResponseEntity<Page<SolicitudesDTO>>) responseDTOService.response(solicitudesService.findByFechaSolicitudBetween(fechaInicio, fechaFin, page, size, columnFilter, direction, user), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "data found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SolicitudesDTO.class))}),
            @ApiResponse(responseCode = "401", description = "debe iniciar session",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "error al solicitar informacion",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/findByEstadoId")
    public ResponseEntity<Page<SolicitudesDTO>> findByEstadoId
            (@RequestParam("estadoId") Long estadoId,
             @RequestParam(name = "page", defaultValue = "0") int page,
             @RequestParam(name = "size", defaultValue = "10") int size,
             @RequestParam(name = "columFilter", defaultValue = "id") String columFilter,
             @RequestParam(name = "direction", defaultValue = "ASC") Sort.Direction direction,
             @RequestParam("user")String user) {
        return (ResponseEntity<Page<SolicitudesDTO>>) responseDTOService.response(solicitudesService
                .findByEstadoId(estadoId, page, size, columFilter, direction, user), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Solicitud creada exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuariosDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Debe iniciar sesion para crear la Solicitud",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al crear Solicitud",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @PostMapping("/saveSolicitud")
    public ResponseEntity<SolicitudesDTO> saveSolicitud(@Valid @RequestBody SolicitudesRequest solicitudesRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return (ResponseEntity<SolicitudesDTO>) responseDTOService.response(HttpStatus.BAD_REQUEST);
        } else {
            return (ResponseEntity<SolicitudesDTO>) responseDTOService.response(solicitudesService.crear(solicitudesRequest), HttpStatus.CREATED);
        }
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Solicitud actualizada exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuariosDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Debe iniciar sesion para actualizar",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al actualizar",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @PutMapping("/updateSolicitud")
    public ResponseEntity<SolicitudesDTO> updateSolicitudes(@Valid @RequestBody ActualizarSolicitudesRequest actualizarSolicitudesRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return (ResponseEntity<SolicitudesDTO>) responseDTOService.response(HttpStatus.BAD_REQUEST);
        } else {
            return (ResponseEntity<SolicitudesDTO>) responseDTOService.response(solicitudesService.actualizar(actualizarSolicitudesRequest), HttpStatus.OK);
        }
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "data found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SolicitudesDTO.class))}),
            @ApiResponse(responseCode = "401", description = "debe iniciar session",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "error al solicitar",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete-Solicitudes")
    public ResponseEntity<String> delete(@RequestParam("id") long id,
                                         @RequestParam("user")String user) {
        return (ResponseEntity<String>) responseDTOService.response(solicitudesService.eliminar(id, user), HttpStatus.OK);

    }


}
