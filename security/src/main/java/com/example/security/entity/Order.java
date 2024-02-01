package com.example.security.entity;

import com.example.common.config.Constants;
import com.example.common.entity.BaseStoreEntity;
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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="order")
public class Order extends BaseStoreEntity {
    @Column(name ="account_id")
    private String accountId;
    @Column(name="customer_id")
    private String customerId;
    @Column(name ="ispay")
    private int isPaid;
    @Column(name ="Total_price")
    private String totalPrice;
    @Column(name = "amount_paid")
    private String amountPaid;
    @Column(name = "remaining_payment")
    private  String remainingPayment;
    @Column(name = "recipient_name")
    private String recipientName;
    @Column(name = "address")
    private String address;
    private String status;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedNow = now.format(formatter);
        setCreatedDate(formattedNow);
        setModifiedDate(formattedNow);
        setCreatedUser(ThreadContext.getCustomUserDetails().getUsername());
        setStatus(Constants.ORDER_STATUS_CREATED);
    }
    @PreUpdate
    protected void onUpdate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedNow = now.format(formatter);
        setModifiedDate(formattedNow);
        setModifiedUser(ThreadContext.getCustomUserDetails().getUsername());
    }
}
