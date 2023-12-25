package com.example.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.entity.Province;

@Repository
public interface ProvincesRepo extends JpaRepository<Province, String> {
    
}
