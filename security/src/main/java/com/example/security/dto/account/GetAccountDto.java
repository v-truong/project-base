package com.example.security.dto.account;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAccountDto {
    private String id;
    private String name;

    private String username;
    private String Phone;
    private String roles;
    private String avatar;
    private String token;
}
