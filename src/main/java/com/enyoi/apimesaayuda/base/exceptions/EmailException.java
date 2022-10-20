package com.enyoi.apimesaayuda.base.exceptions;

public class EmailException extends RuntimeException {
    private static final String DESCRIPTION = "Error correo";

    public EmailException(String mensaje) {
        super(String.format("%s. %s", DESCRIPTION, mensaje));
    }
}
