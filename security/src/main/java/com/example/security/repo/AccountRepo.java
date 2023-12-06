package com.example.security.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.security.entity.Account;

public interface AccountRepo extends JpaRepository<Account,String> {
    Optional<Account> findByUsername(String username);
}
