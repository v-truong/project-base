package com.example.security.ctrl;

import com.example.security.entity.Brand;
import com.example.security.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brand")
public class BrandCtrl {
    @Autowired private BrandService brandService;
    @GetMapping("/getall")
    public List<Brand> getall() {
        return brandService.getAll();
    }



}
