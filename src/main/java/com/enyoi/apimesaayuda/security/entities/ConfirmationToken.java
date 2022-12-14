package com.enyoi.apimesaayuda.security.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Basic(optional = false)
    private String token;
    @Basic(optional = false)
    private LocalDateTime createdAt;
    @Basic(optional = false)
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;
    @Basic(optional = false)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Usuarios user;
}
