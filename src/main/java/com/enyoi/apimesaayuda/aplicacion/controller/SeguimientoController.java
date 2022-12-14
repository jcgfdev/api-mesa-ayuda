package com.enyoi.apimesaayuda.aplicacion.controller;

import com.enyoi.apimesaayuda.aplicacion.dtos.EstadosSolicitudDTO;
import com.enyoi.apimesaayuda.aplicacion.dtos.SeguimientosDTO;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.ActualizarSeguimientosRequest;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.SeguimientosRequest;
import com.enyoi.apimesaayuda.aplicacion.services.ISeguimientosService;
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
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/seguimientos")
@RequiredArgsConstructor
public class SeguimientoController {

    private final ResponseDTOService responseDTOService;

    private final ISeguimientosService seguimientosService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "data found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SeguimientosDTO.class))}),
            @ApiResponse(responseCode = "401", description = "debe iniciar session",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "error al solicitar informacion",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/obtener-todos-seguimientos")
    public ResponseEntity<List<SeguimientosDTO>> obtenerTodos(@RequestParam(name = "user")String user) {
        return (ResponseEntity<List<SeguimientosDTO>>) responseDTOService.response(seguimientosService.findAll(user), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "data found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SeguimientosDTO.class))}),
            @ApiResponse(responseCode = "401", description = "debe iniciar session",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "error al solicitar informacion",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/fechaSolicitud")
    public ResponseEntity<Page<SeguimientosDTO>> findBySolicitudesId(@RequestParam(value = "solicitudesId")Long solicitudesId,
                                                                            @RequestParam(name = "page", defaultValue = "0") int page,
                                                                            @RequestParam(name = "size", defaultValue = "10") int size,
                                                                            @RequestParam(name = "columnFilter", defaultValue = "id") String columnFilter,
                                                                            @RequestParam(name = "direction", defaultValue = "ASC") Sort.Direction direction,
                                                                            @RequestParam(name = "user")String user){
        return (ResponseEntity<Page<SeguimientosDTO>>) responseDTOService.response(seguimientosService.findBySolicitudesId(solicitudesId,page,size,columnFilter,direction,user),HttpStatus.OK );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Seguimiento creado exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuariosDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Debe iniciar sesion para crear el seguimiento",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al crear Seguimiento",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO')")
    @PostMapping("/saveSeguimiento")
    public ResponseEntity<SeguimientosDTO> saveSeguimiento(@Valid @RequestBody SeguimientosRequest seguimientosRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return (ResponseEntity<SeguimientosDTO>) responseDTOService.response(HttpStatus.BAD_REQUEST);
        } else {
            return (ResponseEntity<SeguimientosDTO>) responseDTOService.response(seguimientosService.crear(seguimientosRequest), HttpStatus.CREATED);
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO')")
    @PutMapping("/actualizar-seguimiento")
    public ResponseEntity<SeguimientosDTO> actualizarSeguimiento(
            @Valid @RequestBody ActualizarSeguimientosRequest actualizarSeguimientosRequest,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return (ResponseEntity<SeguimientosDTO>) responseDTOService.response(HttpStatus.BAD_REQUEST);
        } else {
            return (ResponseEntity<SeguimientosDTO>) responseDTOService.response(seguimientosService.actualizar(
                    actualizarSeguimientosRequest), HttpStatus.OK);
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
    @DeleteMapping("/delete-seguimiento")
    public ResponseEntity<String> delete(@RequestParam("id") long id,
                                         @RequestParam("usuario") String usuarios){
        return (ResponseEntity<String>)responseDTOService.response(seguimientosService.delete(id,usuarios), HttpStatus.OK);

    }

    

}
