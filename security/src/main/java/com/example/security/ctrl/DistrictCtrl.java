package com.example.security.ctrl;

import org.springframework.web.bind.annotation.*;

import com.example.security.entity.District;
import com.example.security.service.DistrictsService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("/api/v1/district")
public class DistrictCtrl {
    @Autowired DistrictsService districtsService;
    @GetMapping("/getall")
    public List<District> getMethodName() {
        return districtsService.getAllDistricts();
    }
    @GetMapping("/getbyprovincecode")
    public List<District> getbyProvinceCode(@RequestParam("provincecode") String provincecode){
        return districtsService.getAllByProvincecode(provincecode);
    }
}
