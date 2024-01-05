package com.example.security.service;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;

import com.example.security.dto.product.SearchProductRequest;
import com.example.security.entity.Product;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    List<Product> getallProduct();
    Product getProductById(String id) throws NotFoundException;
    Boolean createProduct();
    Boolean deleteByIdProduct(String id) throws NotFoundException;
    Page<Product> advanceSearch(String filter, SearchProductRequest searchProductRequest, Pageable pageable);
}
