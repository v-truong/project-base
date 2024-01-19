package com.example.security.entity;

import com.example.common.entity.BaseStoreEntity;
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
@Table(name="wh_product_item")
public class WhProductItem extends BaseStoreEntity {
    private String whId;
    private String productItemId;
    private Integer quantity;//so sản pham trong kho
    private Integer lockedQuantity;//so sản pham khong đc phép xuất(hỏng,chờ đc đặt VVVV)

}
