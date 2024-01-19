package com.example.security.service.Implement;

import com.example.common.config.Constants;
import com.example.common.model.ThreadContext;
import com.example.security.dto.brand.CreateBandRequest;
import com.example.security.dto.brand.UpdateBrandRequest;
import com.example.security.entity.Brand;
import com.example.security.entity.Product;
import com.example.security.entity.Technical;
import com.example.security.repo.BrandRepo;
import com.example.security.service.BrandService;
import com.sun.jdi.request.DuplicateRequestException;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BrandImpl implements BrandService {
    @Autowired
    BrandRepo brandRepo;
    @Override
    public List<Brand> getAll() {
        return brandRepo.findAllByIsDelete(Constants.ISDELETE_TRUE);
    }
    @Override
    public String create(CreateBandRequest request) throws NotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        System.out.println(ThreadContext.getCustomUserDetails().getRole());
        System.out.println(Constants.ROLE_SALESPERSON);
           if(!ThreadContext.getCustomUserDetails().getRole().equals(Constants.ROLE_SALESPERSON)){
               throw new AccessDeniedException("api.error.API-008");
           }
           if(request.getLogo().isEmpty()||request.getLogo()==null
           ||request.getName().isEmpty()||request.getName()==null){
               throw new NotFoundException();
           }
           Brand brand=new Brand();
           PropertyUtils.copyProperties(brand,request);
           brand.setCreatedUser(ThreadContext.getCustomUserDetails().getUsername());
           brandRepo.save(brand);
        return "success";
    }

    @Override
    public List<Brand> deleteAllListById(List<String> ids) {
        if(!Constants.ROLE_SALESPERSON.equals(ThreadContext.getCustomUserDetails().getRole())){
            throw new AccessDeniedException("ko co quyen try cap");
        }
        List<Brand> lstBrand=new ArrayList<>();
        List<Brand> brands=brandRepo.findAllById(ids);
        Map<String, Brand> brandMapMaps = brands.stream().collect(Collectors.toMap(Brand::getId, Function.identity()));
        for (String brand: ids) {
            Brand brandFor=brandMapMaps.get(brand);
            if(brandFor==null){
            throw new DuplicateKeyException(brand+":  khong ton tai");
            }
            brandFor.setIsDelete(Constants.ISDELETE_FALSE);
            lstBrand.add(brandFor);
        }
        brandRepo.saveAll(lstBrand);
        return brands;
    }

    @Override
    public Brand update(String id, UpdateBrandRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if(!ThreadContext.getCustomUserDetails().getRole().equals(Constants.ROLE_SALESPERSON)){
            throw new AccessDeniedException("khon co quyen truy cap");
        }
        Optional<Brand> brandOptional=brandRepo.findById(id);
        if (!brandOptional.isPresent()){
            throw new DuplicateKeyException("id brand Not fount");
        }
        Brand brandget=brandOptional.get();
        PropertyUtils.copyProperties(brandget,request);


        return brandRepo.save(brandget);
    }


}
