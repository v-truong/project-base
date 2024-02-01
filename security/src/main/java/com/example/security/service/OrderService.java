package com.example.security.service;

import com.example.security.dto.oder.CreateOderRequest;
import com.example.security.entity.Order;
import javassist.NotFoundException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface OrderService {
    List<Order> getByAccountId();
    String create(CreateOderRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;
    String update();
    String approve(String id) throws NotFoundException;
}
