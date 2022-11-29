package com.enyoi.apimesaayuda.security.controllers;

import com.enyoi.apimesaayuda.base.utils.ResponseDTOService;
import com.enyoi.apimesaayuda.security.dtos.UsuariosDTO;
import com.enyoi.apimesaayuda.security.payloads.requests.LoginRequest;
import com.enyoi.apimesaayuda.security.payloads.requests.UsuariosRequest;
import com.enyoi.apimesaayuda.security.payloads.responses.UsuarioLoginResponse;
import com.enyoi.apimesaayuda.security.services.impl.UsuariosService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuariosService userService;
    private final ResponseDTOService responseDTOService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioLoginResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Error en login",
                    content = @Content)})
    @PostMapping("/loginUser")
    public ResponseEntity<UsuarioLoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return (ResponseEntity<UsuarioLoginResponse>) responseDTOService.response(HttpStatus.BAD_REQUEST);
        } else {
            return (ResponseEntity<UsuarioLoginResponse>) responseDTOService.response(userService.loginUsuario(loginRequest), HttpStatus.OK);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuariosDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Error al crear usuario",
                    content = @Content)})
    @PostMapping("/saveUser")
    public ResponseEntity<UsuariosDTO> saveUser(@Valid @RequestBody UsuariosRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return (ResponseEntity<UsuariosDTO>) responseDTOService.response(HttpStatus.BAD_REQUEST);
        } else {
            return (ResponseEntity<UsuariosDTO>) responseDTOService.response(userService.crearUsuario(userRequest), HttpStatus.CREATED);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Confirmacion de correo exitosa",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "500", description = "Error al confirmar correo",
                    content = @Content)})
    @GetMapping("/confirmToken")
    public ResponseEntity<String> confirmToken(@RequestParam("token") String token) {
        return (ResponseEntity<String>) responseDTOService.response(userService.confirmarToken(token), HttpStatus.ACCEPTED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Confirmacion de correo exitosa",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "500", description = "Error al confirmar correo",
                    content = @Content)})
    @GetMapping("/activarUsuario")
    public ResponseEntity<String> activarUsuarioid(@RequestParam(name = "id")Long userId) {
        return (ResponseEntity<String>) responseDTOService.response(userService.activarUsuarioId(userId), HttpStatus.ACCEPTED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Confirmacion de correo exitosa",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuariosDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Error al confirmar correo",
                    content = @Content)})
    @GetMapping("/cambiarClave")
    public ResponseEntity<UsuariosDTO> cambiarClave(@RequestParam(name = "id")Long userId,
                                                    @RequestParam(name = "newClave")String newClave,
                                                    @RequestParam(name = "RenewClave")String renewClave   ) {
        return (ResponseEntity<UsuariosDTO>) responseDTOService.response(userService.cambiarClave(userId, newClave, renewClave), HttpStatus.ACCEPTED);
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Confirmacion de correo exitosa",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "500", description = "Error al confirmar correo",
                    content = @Content)})
    @GetMapping("/recuperarClaveEmail")
    public ResponseEntity<String> recuperarClaveEmail(@RequestParam(name = "email")String email,
                                                      @RequestParam(name = "url")String url) {
        return (ResponseEntity<String>) responseDTOService.response(userService.recuperarClaveEmail(email, url), HttpStatus.ACCEPTED);
    }

}
