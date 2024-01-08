package com.example.security.repo;

import com.example.security.entity.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerAddressRepo extends JpaRepository<CustomerAddress,String> {
    List<CustomerAddress> findAllByCustomerId(String CustomerId,int isDelete);
}
