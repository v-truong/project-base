package com.example.security.entity;

import com.example.common.entity.EntityBase;

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
public class Technical extends EntityBase {
    private String name;
    private String unit;
    private boolean isDelete;
    
    
}
