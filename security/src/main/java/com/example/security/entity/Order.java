package com.example.security.entity;

import com.example.common.entity.EntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="order")
public class Order extends EntityBase {
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
}
