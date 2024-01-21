package com.example.security.dto.technical;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTechnicalRequest {
    @NotNull
    private String storeId;
    @NonNull
    private String name;
    @NotNull
    private String unit;
    private List<String> categoryId;

}
