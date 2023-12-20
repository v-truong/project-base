package com.example.security.service;

import java.lang.reflect.InvocationTargetException;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.example.security.dto.account.Createaccount;
import com.example.security.entity.Account;

public interface AccountSevice {
    String showTest();
    String createAccount(Createaccount createaccount)throws DuplicateKeyException,IllegalAccessException, InvocationTargetException, NoSuchMethodException;
    void saveUserVerificationToken(Account account, String verificationToken);
    String verifyEmail(String token) throws NotFoundException;
    String Postavatar(String id,String avartar) throws NotFoundException;
}
