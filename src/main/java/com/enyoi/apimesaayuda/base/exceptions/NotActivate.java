package com.enyoi.apimesaayuda.base.exceptions;

public class NotActivate extends RuntimeException{
    private static final String NOACTIVADO = " Usuario no se encuentra Activado";

    public NotActivate(String mensaje){
        super(String.format( "%s. %s", NOACTIVADO, mensaje));
    }

}
