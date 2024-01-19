package com.example.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.entity.Province;

import java.util.Optional;

@Repository
public interface ProvinceRepo extends JpaRepository<Province, String> {
    Optional<Province> findByCode(String code);
    
}
