package com.example.security.dto.technical;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTechnicalRequest {
    @NonNull
    private String name;
    @NotNull
    private String unit;
    private String categoryId;

}
