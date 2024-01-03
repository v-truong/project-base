package com.example.security.service.Implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.entity.Ward;
import com.example.security.repo.WardRepo;
import com.example.security.service.WardsService;
@Service
public class WardsImpl implements WardsService{
    @Autowired private WardRepo wardRepo;
    @Override
    public List<Ward> getAllWards() {
        List<Ward> wards = wardRepo.findAll();
        return wards;
    }

    @Override
    public List<Ward> getAllByDistrictCode(String districtCode) {

        return wardRepo.findAllByDistrictCode(districtCode);
    }


}
