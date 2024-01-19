package com.example.security.dto.warehouse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateWarehouseRequest {
    private String storeId;
    private String name;
    private String pCode;//mã tỉnh thành
    private String dCode;//mã quạn huyện
    private String WCode;//mã Phường xã
    private String detailAddress;//dịa chỉ cụ thể số nhà vvvvv
    private String phone;//só dt quan ly kho
}
