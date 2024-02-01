package com.example.security.repo;

import java.util.List;

import com.example.security.entity.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.entity.Product;

@Repository
public interface ProductItemRepo extends JpaRepository<ProductItem,String>{
    // List<Product> findAllByIsDelete(Boolean isDelete);
    List<ProductItem> findByIdIn(List<String> ids);
    List<ProductItem> findAllByProductIdAndIsDelete(String id,int isdelete);
}
