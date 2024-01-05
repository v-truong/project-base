package com.example.security.service.Implement;

import com.example.common.model.ThreadContext;
import com.example.security.entity.Order;
import com.example.security.repo.OrderRepo;
import com.example.security.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderImpl implements OrderService {
    @Autowired private OrderRepo orderRepo;


    @Override
    public List<Order> getByAccountId() {

        return orderRepo.findAllByAccountId(ThreadContext.getCustomUserDetails().getId());
    }
}
