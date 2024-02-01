package com.example.security.dto.orderdetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDetailRequest {
    private String storeId;

    private String orderId;

    private String productId;

    private Integer quantity;
}
