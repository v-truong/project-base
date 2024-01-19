package com.example.security.dto.store;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStoreRequest {
    private String id;
    private String name;
    private List<String> staffIds;//ids account nv
}
