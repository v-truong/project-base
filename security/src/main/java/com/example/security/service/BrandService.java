package com.example.security.service;

import com.example.security.dto.brand.CreateBandRequest;
import com.example.security.entity.Brand;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;


import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface BrandService {
    List<Brand> getAll();
    String create(CreateBandRequest request) throws NotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException;
    String deleteAllListById(List<String> Ids);

}
