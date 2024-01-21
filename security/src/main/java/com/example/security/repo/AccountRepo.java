package com.example.security.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.security.entity.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Account,String> {
    Optional<Account> findByUsername(String username);
    Optional<Account> findByEmail(String email);
    List<Account> findByIdIn(List<String> ids);


    Page<Account> findAll(Specification<Account> spec, Pageable pageable);
}
