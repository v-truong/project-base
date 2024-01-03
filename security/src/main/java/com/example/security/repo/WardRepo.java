package com.example.security.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.security.entity.Ward;


@Repository
public interface WardRepo extends JpaRepository<Ward, String>{
    List<Ward> findAllByDistrictCode(String districtCode);
}
