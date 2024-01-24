package com.example.security.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchAddressRequest {
    private String addressId;
    private String customerId;
    private String provinceCode;
    private String districtCode;
    private String wardCode;
    private String additionInfo;
    private String receiver;
    private String fullAddress;
    private int isDelete;
}
