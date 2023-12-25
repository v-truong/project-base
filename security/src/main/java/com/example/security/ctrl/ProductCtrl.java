package com.example.security.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.entity.Product;
import com.example.security.repo.ProductRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/api/v1/product")
public class ProductCtrl {
    @Autowired private ProductRepo productRepo;
    @GetMapping("/{id}")
    public List<Product> getMethodName(@PathVariable("id") String param) {
        return productRepo.findAllByName(param);
    }
    
    
}
