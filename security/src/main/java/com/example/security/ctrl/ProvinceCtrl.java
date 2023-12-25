package com.example.security.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.entity.Province;
import com.example.security.service.ProvincesService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/province")
public class ProvinceCtrl {
    @Autowired ProvincesService provincesService;
    @GetMapping("getall")
    public List<Province> getMethodName(@RequestParam String param) {
        return provincesService.GetAllProvinces();
    }
    
    
}
