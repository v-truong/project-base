package com.example.security.service.Implement;

import com.example.common.config.Constants;
import com.example.common.model.ThreadContext;
import com.example.security.dto.brand.CreateBandRequest;
import com.example.security.entity.Brand;
import com.example.security.entity.Product;
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
import java.util.List;
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
           if(ThreadContext.getCustomUserDetails().getRole()!=Constants.ROLE_SALESPERSON){
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
    public String deleteAllListById(List<String> Ids) {
        if(!Constants.ROLE_SALESPERSON.equals(ThreadContext.getCustomUserDetails().getRole())){
            throw new DuplicateKeyException("ko co quyen try cap");
        }
        List<Brand> brands=brandRepo.findAllById(Ids);
        for (Brand brand: brands) {
            brand.setIsDelete(Constants.ISDELETE_FALSE);
        }
        brandRepo.saveAll(brands);
        return "success";
    }


}
