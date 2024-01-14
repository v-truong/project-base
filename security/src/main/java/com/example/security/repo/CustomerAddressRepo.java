package com.example.security.repo;

import com.example.security.entity.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomerAddressRepo extends JpaRepository<CustomerAddress,String> {
    List<CustomerAddress> findAllByCustomerIdAndIsDelete(String CustomerId,int isDelete);
}
