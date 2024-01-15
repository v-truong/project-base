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
@Table(name="warehouse")
public class Warehouse extends BaseStoreEntity {
    @Column(name="name")
    private String name;
    @Column(name = "quantity")
    private String quantity;
    private String pCode;
    private String vcode;



}
