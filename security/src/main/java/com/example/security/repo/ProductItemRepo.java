package com.example.security.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.entity.Product;

@Repository
public interface ProductItemRepo extends JpaRepository<Product,String>{
    // List<Product> findAllByIsDelete(Boolean isDelete);
    
}
