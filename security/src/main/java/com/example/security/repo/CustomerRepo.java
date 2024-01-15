package com.example.security.repo;

import com.example.security.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomerRepo extends JpaRepository<Customer,String> {
    Page<Customer> findAllByIsDelete(Pageable pageable, List<Specification<Customer>> specList, int isDelete);
}
