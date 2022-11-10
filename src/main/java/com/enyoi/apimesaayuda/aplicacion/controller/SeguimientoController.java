package com.enyoi.apimesaayuda.aplicacion.controller;

import com.enyoi.apimesaayuda.aplicacion.dtos.EstadosSolicitudDTO;
import com.enyoi.apimesaayuda.aplicacion.dtos.SeguimientosDTO;
import com.enyoi.apimesaayuda.aplicacion.dtos.SolicitudesDTO;
import com.enyoi.apimesaayuda.aplicacion.services.ISeguimientosService;
import com.enyoi.apimesaayuda.base.utils.ResponseDTOService;
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
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/Seguimientos")
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
    public ResponseEntity<List<SeguimientosDTO>> obtenerTodos() {
        return (ResponseEntity<List<SeguimientosDTO>>) responseDTOService.response(seguimientosService.findAll(), HttpStatus.OK);
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
                                                                            @RequestParam(name = "direction", defaultValue = "ASC") Sort.Direction direction){
        return (ResponseEntity<Page<SeguimientosDTO>>) responseDTOService.response(seguimientosService.findBySolicitudesId(solicitudesId,page,size,columnFilter,direction),HttpStatus.OK );
    }



    

}
