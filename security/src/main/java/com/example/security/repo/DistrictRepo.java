package com.example.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.entity.District;

import java.util.List;

@Repository
public interface DistrictRepo extends JpaRepository<District,String> {
    List<District> findAllByProvinceCode(String provinceCode);

    

}
