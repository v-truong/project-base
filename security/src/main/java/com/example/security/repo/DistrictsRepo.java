package com.example.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.entity.District;
@Repository
public interface DistrictsRepo extends JpaRepository<District,String> {

    

}
