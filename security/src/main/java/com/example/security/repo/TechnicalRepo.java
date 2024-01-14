package com.example.security.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.security.entity.Technical;

@Repository
public interface TechnicalRepo extends JpaRepository<Technical,String>{


     List<Technical> findAllByIsDelete(Integer isDelete);
     Optional<Technical> findByName(String name);
    
}
