package com.example.security.dto.productitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductItemRequest {
    private String Name;
    private String price;
    private String productId;
    private String technicalId;
    private String quantity;
}
