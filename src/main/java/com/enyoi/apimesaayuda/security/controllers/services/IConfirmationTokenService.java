package com.enyoi.apimesaayuda.security.controllers.services;

import com.enyoi.apimesaayuda.security.entities.ConfirmationToken;

import java.util.Optional;

public interface IConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken confirmationToken);

    Optional<ConfirmationToken> findConfirmationTokenByToken(String token);

    int setTokenConfirmedAt(String token);
}
