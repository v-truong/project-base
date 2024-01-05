package com.example.security.service;

import com.example.security.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getByAccountId();
}
