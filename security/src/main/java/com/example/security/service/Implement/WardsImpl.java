package com.example.security.service.Implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.entity.Ward;
import com.example.security.repo.WardsRepo;
import com.example.security.service.WardsService;
@Service
public class WardsImpl implements WardsService{
    @Autowired private WardsRepo wardsRepo;
    @Override
    public List<Ward> getAllWards() {
        List<Ward> wards = wardsRepo.findAll();
        return wards;
    }

    
}
