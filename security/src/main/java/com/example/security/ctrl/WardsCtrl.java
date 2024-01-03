package com.example.security.ctrl;

import java.util.List;

import com.example.security.entity.District;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.security.entity.Ward;
import com.example.security.service.WardsService;


@RestController
@RequestMapping("/api/v1/ward")
public class WardsCtrl {
    @Autowired private WardsService wardsService;
    @GetMapping("/getall")
    public List<Ward> getMethodName() {
        return wardsService.getAllWards();
    }
    @GetMapping("/getbydistrictcode")
    public List<Ward> getbyProvinceCode(@RequestParam("districtcode") String districtcode){
        return wardsService.getAllByDistrictCode(districtcode);
    }

    
}
