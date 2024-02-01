package com.example.security.service;

import com.example.security.dto.productitem.CreateProductItemRequest;
import com.example.security.dto.productitem.UpdateProductItemRequest;
import com.example.security.entity.ProductItem;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ProductItemService {
    String create(CreateProductItemRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;
    String delete(List<String> ids);
    List<ProductItem> getDetailByPoductId(String productId);
    String update(String id,UpdateProductItemRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;

}
