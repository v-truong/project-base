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
public class Technical extends BaseStoreEntity {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "unit", nullable = false)
    private String unit;
    private String categoryId;
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
