package com.example.security.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchCustomerRequest {
    String customerId;
    String fullname;
    String username;
    String email;
    String phone;
}
