package com.example.security.entity;

import com.example.common.entity.EntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="address")
public class CustomerAddress extends EntityBase {

    @Column(name = "address_id")
    private String AddressId;
    @Column(name = "customer_id")
    private String customerId;
    @Column(name = "province_code")
    private String ProvinceCode;
    @Column(name = "district_code")
    private String DistrictCode;
    @Column(name = "ward_code")
    private String WardCode;
    @Column(name = "additionInfo")
    private String AdditionInfo;
    @Column(name = "receiver")
    private String Receiver;
    @Column(name = "full_address")
    private String FullAddress;
    
}
