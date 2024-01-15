package com.example.security.entity;

import com.example.common.entity.BaseStoreEntity;
import com.example.common.entity.EntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="orderdetail")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class OrderDetail extends BaseStoreEntity {
    @Column(name = "oder_id")
    private String orderId;
    @Column(name = "productId")
    private String productId;
    @Column(name = "quantity")
    private Integer quantity;
}
