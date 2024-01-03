package com.example.security.service.Implement;

import com.example.common.config.Constants;
import com.example.common.model.ThreadContext;
import com.example.security.dto.brand.CreateBandRequest;
import com.example.security.entity.Brand;
import com.example.security.entity.Product;
import com.example.security.repo.BrandRepo;
import com.example.security.service.BrandService;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
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
           if(ThreadContext.getCustomUserDetails().getRole()!=Constants.ROLE_ADMIN){
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



}
