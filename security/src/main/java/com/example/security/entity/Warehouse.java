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
    @Column(name="name", updatable = true, nullable = false)
    private String name;
    @Column(name="p_code", updatable = true, nullable = false)
    private String pCode;//mã tỉnh thành
    @Column(name="d_code", updatable = true, nullable = false)
    private String dCode;//mã quạn huyện
    @Column(name="W_code", updatable = true, nullable = false)
    private String wCode;//mã Phường xã
    @Column(name="detail_address", updatable = true, nullable = false)
    private String detailAddress;//dịa chỉ cụ thể số nhà vvvvv
    @Column(name="phone", updatable = true, nullable = false)
    private String phone;//só dt quan ly kho

}
