package com.example.security.repo;

import com.example.security.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetaillRepo extends JpaRepository<OrderDetail,String> {
}
