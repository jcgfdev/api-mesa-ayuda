package com.enyoi.apimesaayuda.base.exceptions;

public class NotDataFound extends RuntimeException {
    private static final String DESCRIPTION = "datos no encontrados";

    public NotDataFound(String mensaje){
        super(String.format("%s. %s", DESCRIPTION, mensaje));
    }
}
