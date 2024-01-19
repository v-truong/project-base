package com.example.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class BaseStoreEntity extends EntityBase{
    @Column(name="store_id", updatable = true, nullable = false)
    private String storeId;
}
