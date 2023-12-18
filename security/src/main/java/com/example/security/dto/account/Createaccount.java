package com.example.security.dto.account;

import java.time.LocalDateTime;

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
    private String id;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String createdUser;
    private String modifiedUser;
    private String name;
    private String username;
    private String password;
    private String roles;
    private String email;
    private boolean isEnabled = false;
}
