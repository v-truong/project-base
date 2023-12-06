package com.example.security.service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.apache.commons.beanutils.PropertyUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security.dto.Createaccount;
import com.example.security.entity.Account;
import com.example.security.repo.AccountRepo;
@Service
public class AccountSeviceImpl implements AccountSevice {
    @Autowired private AccountRepo accountRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public String showTest() {
        
    return "ưaesdkhwkldhjiowe";

    }
    @Override
    public String createAccount(Createaccount createaccount)throws  IllegalAccessException, InvocationTargetException, NoSuchMethodException{
        Optional<Account> account =accountRepo.findByUsername(createaccount.getUsername());
        if(account.isPresent()){
        throw new DuplicateKeyException("API-004");
        }else{
            Account ac =new Account();
            PropertyUtils.copyProperties(ac, createaccount);
            ac.setCreatedDate(LocalDateTime.now());
            ac.setModifiedDate(LocalDateTime.now());
            ac.setCreatedUser(createaccount.getUsername());
            ac.setPassword(passwordEncoder.encode(ac.getPassword()));
            accountRepo.save(ac);
            return "ok";
        }

    }
}
