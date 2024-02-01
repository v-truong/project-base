package com.example.security.entity;

import com.example.common.entity.EntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category_technical")
public class CategoryTechnical extends EntityBase {
    private String categoryId;
    private String technicalId;

}
