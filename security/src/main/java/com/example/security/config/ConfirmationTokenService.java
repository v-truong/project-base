package com.example.security.config;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.entity.ConfirmationToken;
import com.example.security.repo.ConfirmationTokenRepository;

@Service
public class ConfirmationTokenService {
    @Autowired
    private  ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {

        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> findToken(String token) {

        return confirmationTokenRepository.findByToken(token);
    }

    public void setConfirmedAt(String token) {

        confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
