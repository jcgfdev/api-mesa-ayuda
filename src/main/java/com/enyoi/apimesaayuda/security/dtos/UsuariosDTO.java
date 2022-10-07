package com.enyoi.apimesaayuda.security.dtos;

import com.enyoi.apimesaayuda.security.entities.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuariosDTO {
    private Long id;
    private String nombres;
    private String apellidos;
    private String email;
    private String clave;
    private Set<Roles> roles;
    private Boolean bloqueado;
    private Boolean activado;
}
