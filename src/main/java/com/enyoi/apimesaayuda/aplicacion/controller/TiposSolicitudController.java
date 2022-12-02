package com.enyoi.apimesaayuda.aplicacion.controller;

import com.enyoi.apimesaayuda.aplicacion.dtos.TiposSolicitudDTO;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.CrearTiposSolicitudRequest;
import com.enyoi.apimesaayuda.aplicacion.payloads.requests.TiposSolicitudRequest;
import com.enyoi.apimesaayuda.aplicacion.services.ITiposSolicitudService;
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
@RequestMapping("/tipos-solicitud")
@RequiredArgsConstructor
public class TiposSolicitudController {

    private final ResponseDTOService responseDTOService;

    private final ITiposSolicitudService tiposSolicitudService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitud exitosa",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TiposSolicitudDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Debe iniciar sesion para obtener la informaicion",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al solicitar informacion",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/obtener-todos")
    public ResponseEntity<List<TiposSolicitudDTO>> obtenerTodos(@RequestParam("user") String user) {
        return (ResponseEntity<List<TiposSolicitudDTO>>) responseDTOService.response(tiposSolicitudService.findAll(user), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitud exitosa",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TiposSolicitudDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Debe iniciar sesion para obtener la informaicion",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al solicitar informacion",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/obtener-uno")
    public ResponseEntity<TiposSolicitudDTO> obtenerUno(@RequestParam("id") Long id,
                                                        @RequestParam("user") String user) {
        return (ResponseEntity<TiposSolicitudDTO>) responseDTOService.response(tiposSolicitudService.findById(id, user), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitud exitosa",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TiposSolicitudDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Debe iniciar sesion para obtener la informaicion",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al solicitar informacion",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TECNICO') or hasRole('ROLE_USUARIO')")
    @GetMapping("/obtener-uno-por-tipo")
    public ResponseEntity<TiposSolicitudDTO> obtenerUno(@RequestParam("tipo") String tipo,
                                                        @RequestParam("user") String user) {
        return (ResponseEntity<TiposSolicitudDTO>) responseDTOService.response(tiposSolicitudService.findByTipoSolicitud(tipo,user), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo_solicitud creada exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuariosDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Debe iniciar sesion para crear el Tipo_solicitud",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Sin privilegios suficientes",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error al crear Tipo_solicitud",
                    content = @Content)})
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/saveTipoSolicitud")
    public ResponseEntity<TiposSolicitudDTO> saveUser(@Valid @RequestBody CrearTiposSolicitudRequest crearTiposSolicitudRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return (ResponseEntity<TiposSolicitudDTO>) responseDTOService.response(HttpStatus.BAD_REQUEST);
        } else {
            return (ResponseEntity<TiposSolicitudDTO>) responseDTOService.response(tiposSolicitudService.create(crearTiposSolicitudRequest), HttpStatus.CREATED);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo_solicitud actualizada exitosamente",
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
    @PutMapping("/update-tipo-solicitud")
    public ResponseEntity<TiposSolicitudDTO> updateTipoSolicitud(@Valid @RequestBody TiposSolicitudRequest tiposSolicitudRequests, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return (ResponseEntity<TiposSolicitudDTO>) responseDTOService.response(HttpStatus.BAD_REQUEST);
        } else {
            return (ResponseEntity<TiposSolicitudDTO>) responseDTOService.response(tiposSolicitudService.update(tiposSolicitudRequests), HttpStatus.OK);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo_solicitud eliminada exitosamente",
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
    public ResponseEntity<String> eliminarUno(@RequestParam("id") long id,
                                              @RequestParam("user") String user) {
        return (ResponseEntity<String>) responseDTOService.response(tiposSolicitudService.delete(id, user), HttpStatus.OK);
    }
}
