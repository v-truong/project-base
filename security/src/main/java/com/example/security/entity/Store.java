package com.example.security.entity;

import com.example.common.entity.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Table(name = "store")
public class Store extends EntityBase {
    private  String name;
    private String ownerId;
    private String staffIds;
}
