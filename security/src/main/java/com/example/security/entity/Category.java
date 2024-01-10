package com.example.security.entity;

import com.example.common.entity.EntityBase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity 
@Table(name="category")
public class Category extends EntityBase {
    @Column(name="name", updatable = false, nullable = false)
    private String name;
}
