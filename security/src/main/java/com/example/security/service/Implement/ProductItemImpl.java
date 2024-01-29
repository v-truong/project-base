package com.example.security.service.Implement;

import com.example.security.dto.productitem.CreateProductItemRequest;
import com.example.security.entity.ProductItem;
import com.example.security.repo.ProductItemRepo;
import com.example.security.service.ProductItemService;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
@Service
public class ProductItemImpl implements ProductItemService {
    @Autowired
    ProductItemRepo productItemRepo;
    @Override
    public String create(CreateProductItemRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        ProductItem productItem =new ProductItem();
        PropertyUtils.copyProperties(productItem,request);
        productItemRepo.save(productItem);

        return "Success";
    }

    @Override
    public String delete(List<String> ids) {
        return null;
    }

    @Override
    public ProductItem getDetailByPoductId(String id) {
        return null;
    }

    @Override
    public String update() {
        return null;
    }
}
