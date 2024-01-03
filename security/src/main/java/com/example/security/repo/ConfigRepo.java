package com.example.security.repo;

import com.example.security.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepo extends JpaRepository<Config,String> {

}
