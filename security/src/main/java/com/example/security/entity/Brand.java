package com.example.security.entity;

import com.example.common.entity.EntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "brand")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Brand extends EntityBase {
    private String name;
    @Column(name = "description")
    private  String description;
    private String logo;


}
