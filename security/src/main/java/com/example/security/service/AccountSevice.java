package com.example.security.service;

import java.lang.reflect.InvocationTargetException;

import org.springframework.dao.DuplicateKeyException;
import com.example.security.dto.Createaccount;

public interface AccountSevice {
    public String showTest();
    public String createAccount(Createaccount createaccount)throws DuplicateKeyException,IllegalAccessException, InvocationTargetException, NoSuchMethodException;
    
}
