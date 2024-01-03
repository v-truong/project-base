package com.example.security.repo;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.security.entity.ConfirmationToken;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, String>{
        Optional<ConfirmationToken> findByToken(String token);

    @Modifying
    @Transactional
    @Query(
        value = "UPDATE confirmation_tokens ct SET ct.confirmed_at = ?2 WHERE ct.token = ?1",
        nativeQuery = true
    )
    void updateConfirmedAt(String token, LocalDateTime confirmedAt);
}
