package com.example.security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class District{
    @Id
    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "name_en", length = 255)
    private String nameEn;

    @Column(name = "full_name", length = 255)
    private String fullName;

    @Column(name = "full_name_en", length = 255)
    private String fullNameEn;

    @Column(name = "code_name", length = 255)
    private String codeName;

    @Column(name = "province_code", length = 20)
    private String provinceCode;

    @Column(name = "administrative_unit_id")
    private Integer administrativeUnitId;
}
