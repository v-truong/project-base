package com.example.security.service;

import com.example.security.dto.category.CreateCategoryRequest;
import com.example.security.dto.category.UpdateCategoryRequest;
import com.example.security.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getall();
    String create(CreateCategoryRequest request);
    List<String> findTechnicallCategoryId(String categoryid);
    String update(UpdateCategoryRequest request);
    String delete(List<String> ids);
}
