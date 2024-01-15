package com.example.security.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerRequest {
    String accountId;
    String customerId;
    String storeId;
    String fullname;
    String username;
    String email;
    String phone;
}
