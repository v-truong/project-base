package com.example.security.entity;

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
public class Product extends EntityBase{
    @Column(name = "name")
    private String name;
    // @Column(name = "price")
    // private String price;productitem\
    @Column(name="description")
    private String Description;
    @Column(name="category_Id")
    private String categoryId;
    @Column(name="brand_id")
    private  String brandId;

    

    
}
