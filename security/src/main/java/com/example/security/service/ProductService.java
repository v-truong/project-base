package com.example.security.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.example.security.dto.product.CreateProductRequest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;

import com.example.security.dto.product.SearchProductRequest;
import com.example.security.entity.Product;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    List<Product> getallProduct();
    Product getProductById(String id) throws NotFoundException;
    Boolean createProduct(CreateProductRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;
    Boolean deleteByIdProduct(String id) throws NotFoundException;
    Page<Product> advanceSearch(String filter, SearchProductRequest searchProductRequest, Pageable pageable);
}
