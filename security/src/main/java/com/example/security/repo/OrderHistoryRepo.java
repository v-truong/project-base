package com.example.security.repo;

import com.example.security.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHistoryRepo extends JpaRepository<OrderHistory,String> {

}
