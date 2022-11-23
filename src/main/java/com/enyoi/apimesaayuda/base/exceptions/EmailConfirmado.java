package com.enyoi.apimesaayuda.base.exceptions;

public class EmailConfirmado extends RuntimeException {
    private static final String EMAILCONFIRMADO = "El Email ya se encuentra confirmado";

    public EmailConfirmado(String mensaje){
        super(String.format("%s. %s", EMAILCONFIRMADO, mensaje));
    }
}
