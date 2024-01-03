package com.example.security.service.Implement;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import org.apache.commons.beanutils.PropertyUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security.config.UserInfoUserDetailsService;
import com.example.security.dto.account.Createaccount;
import com.example.security.entity.Account;
import com.example.security.repo.AccountRepo;
import com.example.security.service.AccountSevice;
@Service
public class AccountSeviceImpl implements AccountSevice {
    @Autowired private UserInfoUserDetailsService userInfoUserDetailsService;
    @Autowired private AccountRepo accountRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public String showTest() {
        
    return "ưaesdkhwkldhjiowe";

    }
    @Override
    public String createAccount(Createaccount createaccount)throws  IllegalAccessException, InvocationTargetException, NoSuchMethodException{
        Optional<Account> accountMail = accountRepo.findByEmail(createaccount.getEmail());
        Optional<Account> accountUsernName =accountRepo.findByUsername(createaccount.getUsername());
//        if ()
        if (accountUsernName.isPresent()){
           throw new DuplicateKeyException(
                   "User with email "+createaccount.getEmail() + " already exists");
       }
        if (accountMail.isPresent()){
           throw new DuplicateKeyException(
                   "User with email "+createaccount.getEmail() + " already exists");
       }
       
       Account account =new Account();
       PropertyUtils.copyProperties(account, createaccount);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
       accountRepo.save(account);
        return "Successful registration, please verify email";
    }
    @Override
    public void saveUserVerificationToken(Account account, String token) {
    }
    @Override
    public String verifyEmail(String token) throws NotFoundException {
        Account account = accountRepo.findByToken(token);
        if (account==null) {
          return "saiiiiiiii";
        }
        account.setEnabled(true);
        accountRepo.save(account);
        return"goot";
    }
    @Override
    public String Postavatar(String id,String avartar) throws NotFoundException {
         Account account =accountRepo.findById(id).get();
         if (account==null) {
            throw new NotFoundException();
         }
         account.setAvatar(avartar);
         accountRepo.save(account);
        return "upload avatar success";
    }
}
 