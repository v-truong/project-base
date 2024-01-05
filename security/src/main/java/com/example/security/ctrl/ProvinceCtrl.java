package com.example.security.ctrl;

import org.springframework.web.bind.annotation.*;

import com.example.security.entity.Province;
import com.example.security.service.ProvincesService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("/api/v1/province")
@CrossOrigin
public class ProvinceCtrl {
    @Autowired ProvincesService provincesService;
    @GetMapping("/getall")
    public List<Province> getAll() {
        return provincesService.GetAllProvinces();
    }
}
