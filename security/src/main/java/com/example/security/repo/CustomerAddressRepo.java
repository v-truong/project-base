package com.example.security.repo;

import com.example.security.entity.Customer;
import com.example.security.entity.CustomerAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomerAddressRepo extends JpaRepository<CustomerAddress,String>, JpaSpecificationExecutor<CustomerAddress> {
    Page<CustomerAddress> findAllByCustomerIdAndIsDelete(String customerId, int isDelete, Pageable pageable);
}
