package com.example.common.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    @NotNull
    private Integer status=0;
    @NotNull private String errorCode;
    @NotNull String  errorMsg;
}
