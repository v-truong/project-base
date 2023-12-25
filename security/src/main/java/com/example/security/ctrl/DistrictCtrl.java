package com.example.security.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.entity.District;
import com.example.security.service.DistrictsService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/district")
public class DistrictCtrl {
    @Autowired DistrictsService districtsService;
    @GetMapping("/path")
    public List<District> getMethodName(@RequestParam String param) {
        return districtsService.getAllDistricts();
    }
    
}
