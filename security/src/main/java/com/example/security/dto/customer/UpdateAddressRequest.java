package com.example.security.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAddressRequest {String addressId;
    String customerId;
    String proviceId;
    String districtId;
    String wardId;
    String additionInfo;
    String receiver;
    String fullAddress;
}
