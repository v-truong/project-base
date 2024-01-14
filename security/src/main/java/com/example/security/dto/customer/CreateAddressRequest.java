package com.example.security.dto.customer;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAddressRequest {
     String customerId;
     String provinceCode;
     String districtCode;
     String wardCode;
     String additionInfo;
     String receiver;
     String fullAddress;
}
