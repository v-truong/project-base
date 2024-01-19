package com.example.security.service.Implement;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.Optional;

import com.example.common.model.ThreadContext;
import com.example.security.dto.account.ForgotPasswordRequest;
import com.example.security.dto.account.UpdateAccountRequest;
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
    public String createAccount(Createaccount createaccount) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, NotFoundException {
        Optional<Account> accountMail = accountRepo.findByEmail(createaccount.getEmail());
        if(!accountMail.isPresent()){
            throw new  NotFoundException();
        }
        Account accountget=accountMail.get();
        if (!accountget.getToken().equals(createaccount.getToken())){
            throw new DuplicateKeyException("invalid code");
        }
        PropertyUtils.copyProperties(accountget, createaccount);
        accountget.setPassword(passwordEncoder.encode(accountget.getPassword()));
        accountget.setEnabled(true);
        accountRepo.save(accountget);
        return "Success Please log in again";
    }
    @Override
    public void saveUserVerificationToken(Account account, String token) {
    }
    @Override
    public String verifyEmail(String token) throws NotFoundException {
        Optional<Account> account = accountRepo.findByEmail(token);
        if (account==null) {
          return "saiiiiiiii";
        }
//        account.setEnabled(true);
//        accountRepo.save(account);
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

    @Override
    public String forgotPassword(ForgotPasswordRequest request) throws NotFoundException {
        Account account=accountRepo.findByUsername(request.getUsername()).get();
        if (account==null) {
            throw new NotFoundException();
        }
        if (!account.getEmail().equals(request.getUsername())) {
            throw new NotFoundException();
        }
        return "Password has been sent to email";



        }

    @Override
    public String updateAccount(UpdateAccountRequest request) throws NotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Optional<Account> account=accountRepo.findById(ThreadContext.getCustomUserDetails().getId());
        if (!account.isPresent()){
            throw new NotFoundException();
        }
        Account accountget=account.get();
        PropertyUtils.copyProperties(accountget,request);
//        accountget.setModifiedDate(LocalDateTime.now());
        accountget.setModifiedUser(ThreadContext.getCustomUserDetails().getUsername());
        accountRepo.save(accountget);
        return "Success";
    }

}
 