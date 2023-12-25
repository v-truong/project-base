package com.example.security.service.Implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.entity.Province;
import com.example.security.repo.ProvincesRepo;
import com.example.security.service.ProvincesService;

@Service
public class ProvincesIpml implements ProvincesService{
    @Autowired private ProvincesRepo provincesRepo;

    @Override
    public List<Province> GetAllProvinces() {
        List<Province> listProvinces = provincesRepo.findAll();
        return listProvinces;
    }
}
