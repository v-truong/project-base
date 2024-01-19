package com.example.security.service;

import com.example.security.dto.brand.CreateBandRequest;
import com.example.security.dto.brand.UpdateBrandRequest;
import com.example.security.entity.Brand;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;


import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface BrandService {
    List<Brand> getAll();
    String create(CreateBandRequest request) throws NotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException;
    List<Brand> deleteAllListById(List<String> Ids);
    Brand update(String id, UpdateBrandRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;


}
