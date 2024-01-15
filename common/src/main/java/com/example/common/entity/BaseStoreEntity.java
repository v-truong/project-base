package com.example.common.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.*;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class BaseStoreEntity extends EntityBase{
    private String storeId;
}
