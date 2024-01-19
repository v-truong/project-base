package com.example.security.service.Implement;

import com.example.security.dto.warehouse.CreateWarehouseRequest;
import com.example.security.entity.Province;
import com.example.security.entity.Ward;
import com.example.security.repo.DistrictRepo;
import com.example.security.repo.ProvinceRepo;
import com.example.security.repo.WardRepo;
import com.example.security.repo.WarehouseRepo;
import com.example.security.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WarehouseImpl implements WarehouseService {
    @Autowired private WarehouseRepo warehouseRepo;
    @Autowired private ProvinceRepo provinceRepo;
    @Autowired private WardRepo wardRepo;
    @Autowired private DistrictRepo districtRepo;
    @Override
    public String create(CreateWarehouseRequest request) {
        Optional<Province> provinceOptional =provinceRepo.findByCode(request.getPCode());
        if (!provinceOptional.isPresent()){
            throw new DuplicateKeyException("province not fount");
        }
        Optional<Ward> wardOptional=wardRepo.findByCode(request.getWCode());
        if(!wardOptional.isPresent()){
            throw new DuplicateKeyException("ward not fount");
        }

        return null;
    }
}
