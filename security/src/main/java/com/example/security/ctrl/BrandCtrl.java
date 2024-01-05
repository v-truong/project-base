package com.example.security.ctrl;

import com.example.security.dto.brand.CreateBandRequest;
import com.example.security.entity.Brand;
import com.example.security.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/brand")
@CrossOrigin
public class BrandCtrl {
    @Autowired private BrandService brandService;
    @GetMapping("/getall")
    public List<Brand> getall() {
        return brandService.getAll();
    }
    @PostMapping("create")
    public String create(@RequestBody CreateBandRequest request) throws ChangeSetPersister.NotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return brandService.create(request);
    }



}
