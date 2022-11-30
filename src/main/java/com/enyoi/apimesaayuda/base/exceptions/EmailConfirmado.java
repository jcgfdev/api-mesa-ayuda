package com.enyoi.apimesaayuda.base.exceptions;

public class EmailConfirmado extends RuntimeException {
    private static final String EMAILCONFIR = "El Email ya se encuentra confirmado";

    public EmailConfirmado(String mensaje){
        super(String.format("%s. %s", EMAILCONFIR, mensaje));
    }
}
