package com.example.security.dto.oder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOderRequest {
    private String accountId;
    private String customerId;
    private int isPaid;
    private String totalPrice;
    private String amountPaid;
    private  String remainingPayment;
    private String recipientName;
    private String address;
    private String status;
}
