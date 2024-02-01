package com.example.security.dto.orderdetail;

import com.example.security.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderDetailRequest {

    private String orderId;
    private String productId;
    private Integer quantity;
}
