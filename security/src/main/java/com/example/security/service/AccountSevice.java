package com.example.security.service;

import java.lang.reflect.InvocationTargetException;

import com.example.security.dto.account.*;
import com.example.security.dto.product.SearchProductRequest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.example.security.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountSevice {
    String showTest();
    String createAccount(Createaccount createaccount) throws DuplicateKeyException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, NotFoundException;
    void saveUserVerificationToken(Account account, String verificationToken);
    String verifyEmail(String token) throws NotFoundException;
    String Postavatar(String id,String avartar) throws NotFoundException;
    String forgotPassword(ForgotPasswordRequest request) throws NotFoundException;
    String updateAccount(UpdateAccountRequest request) throws NotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException;
    GetAccountDto getByFilterUsername(String user);
    Page<Account> advanceSearch(String filter, SearchAccountRequetst searchAccountRequetst, Pageable pageable);

}
