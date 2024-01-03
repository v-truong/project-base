package com.example.security.service.Implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.entity.District;
import com.example.security.repo.DistrictRepo;
import com.example.security.service.DistrictsService;

@Service
public class DistrictsImpl implements DistrictsService {
    @Autowired private DistrictRepo districtRepo;
    @Override
    public List<District> getAllDistricts() {
        
        return districtRepo.findAll();
    }

    @Override
    public List<District> getAllByProvincecode(String provincecode) {
        return districtRepo.findAllByProvinceCode(provincecode);
    }

}
