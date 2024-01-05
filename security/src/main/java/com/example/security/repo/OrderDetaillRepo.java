package com.example.security.repo;

import com.example.security.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetaillRepo extends JpaRepository<OrderDetail,String> {
    List<OrderDetail> findAllByOrderId(String oderId);
}
