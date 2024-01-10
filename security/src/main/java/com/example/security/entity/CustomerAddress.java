package com.example.security.entity;

import com.example.common.entity.EntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="customer_address")
public class CustomerAddress extends EntityBase {
    @Column(name = "province_code")
    private String provinceCode;
    @Column(name = "district_code")
    private String districtCode;
    @Column(name = "ward_code")
    private String wardCode;
    @Column(name = "additionInfo")
    private String additionInfo;
    @Column(name = "receiver")
    private String receiver;
    @Column(name = "full_address")
    private String fullAddress;
}
