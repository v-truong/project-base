package com.example.security.dto.category;

import jakarta.persistence.Column;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.List;

public class GetCategoryDto {
    private String id;
    private String name;
    private String createdUser;
    private String modifiedUser;
    private String createdDate;
    private String modifiedDate;
    private List<String> TechnicalIds;
}
