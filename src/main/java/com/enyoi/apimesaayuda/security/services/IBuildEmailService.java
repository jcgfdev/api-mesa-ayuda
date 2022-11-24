package com.enyoi.apimesaayuda.security.services;

public interface IBuildEmailService {
    String buildEmail(String name, String link);
    String recuperarClaveEmail(String name, String link);
}
