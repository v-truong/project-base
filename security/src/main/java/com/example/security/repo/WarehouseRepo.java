package com.example.security.repo;

import com.example.security.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepo extends JpaRepository<Warehouse,String> {
}
