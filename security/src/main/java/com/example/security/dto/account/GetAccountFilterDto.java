package com.example.security.dto.account;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetAccountFilterDto {
    private String name;

    private String username;

    private String password;

    private String email;
    private String phone;

    private String roles;

    private boolean isEnabled = false;
    private String avatar;

}
