package com.example.security.entity;

import com.example.common.config.Constants;
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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Table(name = "store")
public class Store extends EntityBase {
    @Column(name = "name", updatable = true, nullable = false)
    private  String name;
    @Column(name = "owner_id", updatable = true, nullable = false)
    private String ownerId;//account chủ
    @Column(name = "staff_ids")
    private String staffIds;//ids account nv
    @Column(name = "status")
    private Integer status=1;//1 dang hoat dong ,0 tam dung
    private String expireDate;
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedNow = now.format(formatter);
        setCreatedDate(formattedNow);
        setModifiedDate(formattedNow);
        setCreatedUser(ThreadContext.getCustomUserDetails().getUsername());
        LocalDateTime resultDateTime = now.plusSeconds(30*24*60*60);
        String formattedResult = resultDateTime.format(formatter);
        setExpireDate(formattedResult);
    }
    @PreUpdate
    protected void onUpdate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedNow = now.format(formatter);
        setModifiedDate(formattedNow);
    }
}
