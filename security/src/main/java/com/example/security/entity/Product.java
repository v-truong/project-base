package com.example.security.entity;

import com.example.common.entity.BaseStoreEntity;
import com.example.common.entity.EntityBase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseStoreEntity {
    @Column(name = "name")
    private String name;
    // @Column(name = "price")
    // private String price;productitem\
    @Column(name="description")
    private String description;
    @Column(name="category_id")
    private String categoryId;//người mua lấy ra theo category null
    @Column(name="brand_id")
    private  String brandId;
    
}
