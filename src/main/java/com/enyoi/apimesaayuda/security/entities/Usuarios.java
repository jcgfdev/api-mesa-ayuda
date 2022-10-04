package com.enyoi.apimesaayuda.security.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 120)
    @NonNull
    private String nombres;
    @NotBlank
    @Size(max = 120)
    @NonNull
    private String apellidos;
    @NotBlank
    @Size(max = 50)
    @NonNull
    @Email
    private String email;
    @NotBlank
    @Size(max = 120)
    @NonNull
    private String clave;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles = new HashSet<>();
    private Boolean bloqueado = false;
    private Boolean activado = false;
}
