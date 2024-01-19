package com.example.security.dto.brand;

import jakarta.persistence.Column;
import jakarta.persistence.SqlResultSetMapping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBrandRequest {
    private String name;
    private  String description;
    private String logo;
}
