package com.example.security.entity;

import com.example.common.entity.EntityBase;
import com.example.common.model.ThreadContext;
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
@Table(name ="customer_address")
public class CustomerAddress extends EntityBase {
    @Column(name = "address_id")
    private String addressId;
    @Column(name = "customer_id")
    private String customerId;
    @Column(name = "province_code")
    private String provinceCode;
    @Column(name = "district_code")
    private String districtCode;
    @Column(name = "ward_code")
    private String wardCode;
    @Column(name = "additionInfo")
    private String additionInfo;
    @Column(name = "receiverName")
    private String receiverName;
    @Column(name = "receiverPhone")
    private String receiverPhone;
    @Column(name = "receiverEmail")
    private String receiverEmail;
    @Column(name = "full_address")
    private String fullAddress;
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedNow = now.format(formatter);
        setCreatedDate(formattedNow);
        setModifiedDate(formattedNow);
        setCreatedUser(ThreadContext.getCustomUserDetails().getUsername());
    }
    @PreUpdate
    protected void onUpdate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedNow = now.format(formatter);
        setModifiedDate(formattedNow);
    }
}
