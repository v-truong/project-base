package com.example.security.entity;

import com.example.common.entity.BaseStoreEntity;
import com.example.common.entity.EntityBase;

import com.example.common.model.ThreadContext;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    @Column(name="category_parent_id")
    private String  categoryParentId;
    @Column(name="total_views")
    private Integer totalViews;


    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedNow = now.format(formatter);
        setCreatedDate(formattedNow);
        setModifiedDate(formattedNow);
        setCreatedUser(ThreadContext.getCustomUserDetails().getUsername());
        setTotalViews(0);
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
