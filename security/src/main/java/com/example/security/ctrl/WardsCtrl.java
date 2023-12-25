package com.example.security.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.entity.Ward;
import com.example.security.service.WardsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/wards")
public class WardsCtrl {
    @Autowired private WardsService wardsService;
    @PostMapping("/getall")
    public List<Ward> getMethodName(@RequestParam String param) {
        return wardsService.getAllWards();
    }
    
}
