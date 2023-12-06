package com.example.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString(includeFieldNames = true)
public class Tenant {
    private int id;
    private String name;
    private String email;
    private String roles;
}
