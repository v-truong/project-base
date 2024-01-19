package com.example.security.dto.account;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Createaccount {
//    private String id;
    private String name;
    @NotBlank
    @NotNull
    private String username;
    @NotBlank
    @NotNull
    private String password;
    private String roles;
    private String email;
    private Integer token;
}
