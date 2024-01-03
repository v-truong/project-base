package com.example.security.service;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;

import com.example.security.dto.product.SearchProductRequest;
import com.example.security.entity.Product;
import com.example.security.entity.Provider;

public interface ProductService {
    List<Product> getallProduct();
    Product getProductById(String id) throws NotFoundException;
    Boolean createProduct();
    Boolean deleteByIdProduct(String id) throws NotFoundException;
    Page<Provider> advanceSearch(String filter,SearchProductRequest searchProductRequest,Pageable pageable);

}
