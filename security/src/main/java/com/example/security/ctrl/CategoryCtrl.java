package com.example.security.ctrl;

import com.example.security.entity.Category;
import com.example.security.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
