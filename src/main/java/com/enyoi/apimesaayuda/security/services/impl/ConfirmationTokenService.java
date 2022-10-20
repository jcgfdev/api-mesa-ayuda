package com.enyoi.apimesaayuda.security.services.impl;

import com.enyoi.apimesaayuda.security.entities.ConfirmationToken;
import com.enyoi.apimesaayuda.security.repositories.ConfirmationTokenRepository;
import com.enyoi.apimesaayuda.security.services.IConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService implements IConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public Optional<ConfirmationToken> findConfirmationTokenByToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    @Override
    public int setTokenConfirmedAt(String token) {
        return confirmationTokenRepository.actualizarConfirmedAt(token, LocalDateTime.now());
    }
}
