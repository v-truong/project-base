package com.example.security.entity;

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
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity 
@Table(name="category")
public class Category extends BaseStoreEntity {
    @Column(name="name", updatable = true, nullable = false)
    private String name;
    private String parentId;//role 1 truyen null o insert null
    @Column(name="technical_id")
    private String technicalId;

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
        setModifiedUser(ThreadContext.getCustomUserDetails().getUsername());
    }
}
