package com.example.security.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAddressRequest {
    String addressId;
    String customerId;
    String provinceId;
    String districtId;
    String wardId;
    String additionInfo;
    String receiver;
    String fullAddress;
}
