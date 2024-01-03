package com.example.security.service.Implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.entity.Province;
import com.example.security.repo.ProvinceRepo;
import com.example.security.service.ProvincesService;

@Service
public class ProvincesIpml implements ProvincesService{
    @Autowired private ProvinceRepo provinceRepo;

    @Override
    public List<Province> GetAllProvinces() {
         provinceRepo.findAll();
        return provinceRepo.findAll();
    }
}
