package com.example.security.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.security.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends  JpaRepository<Product,String>, JpaSpecificationExecutor<Product> {

   List<Product> findAllByName(String name);
   // @Query("from product e where e.isdelete in ?1")
   // List<Product> findAllbyIsDelete(Boolean isDelete);

}
