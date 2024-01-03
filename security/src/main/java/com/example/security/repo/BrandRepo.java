package com.example.security.repo;

import com.example.security.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepo extends JpaRepository<Brand,String> {
    List<Brand> findAllByIsDelete(int isDelete);
}
