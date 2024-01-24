package com.example.security.dto.technical;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTechnicalRequest {
    @NonNull
    private String name;
    private String storeId;
    @NotNull
    private String unit;
    private List<String> categoryId;

}