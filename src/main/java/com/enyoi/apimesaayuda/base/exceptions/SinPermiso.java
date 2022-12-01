package com.enyoi.apimesaayuda.base.exceptions;

public class SinPermiso extends RuntimeException{
    private static final String SINPERMISO = "Usuario sin permiso";

    public SinPermiso(String mensaje) { super(String.format("%s. %s", SINPERMISO, mensaje));}
}
