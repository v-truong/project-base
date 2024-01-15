package com.example.security.entity;

import java.time.LocalDateTime;

import com.example.common.entity.EntityBase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationToken extends EntityBase {

    @Column(name = "token")
    private String token;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @Column(name = "account")
    private String  account;

}
