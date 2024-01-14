package com.example.security.ctrl;

import com.example.security.dto.category.CreateCategoryRequest;
import com.example.security.entity.Category;
import com.example.security.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@CrossOrigin
public class CategoryCtrl {
    @Autowired private CategoryService categoryService;
    @GetMapping
    public List<Category> getall(){
       return categoryService.getall();
    }
    @PostMapping("create")
    public String create(@RequestBody CreateCategoryRequest request){
        return categoryService.create(request);
    }
}

