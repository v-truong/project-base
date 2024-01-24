package com.example.security.dto.technical;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

import java.util.List;

public class UpdateTechnicalRequest {
    @NotNull
    private String id;
    @NotNull
    private String storeId;
    @NonNull
    private String name;
    @NotNull
    private String unit;
    private List<String> categoryId;
}
