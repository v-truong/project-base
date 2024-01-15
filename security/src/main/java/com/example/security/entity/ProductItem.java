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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="product_item")
public class ProductItem extends BaseStoreEntity {
    @Column(name = "name")
    private String Name;
    @Column(name = "price")
    private String price;
    @Column(name="product_id")
    private String productId;
    @Column(name="technical_id")
    private String technicalId;

}
