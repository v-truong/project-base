package com.example.security.service.Implement;

import com.example.common.model.ThreadContext;
import com.example.security.entity.Order;
import com.example.security.entity.OrderDetail;
import com.example.security.repo.OrderDetaillRepo;
import com.example.security.repo.OrderRepo;
import com.example.security.service.OrderDetailService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailImpl implements OrderDetailService {
    @Autowired private OrderDetaillRepo orderDetaillRepo;
    @Autowired private OrderRepo orderRepo;
    @Override
    public List<OrderDetail> getByOderid(String oderId) throws NotFoundException {
        Optional<Order> order=orderRepo.findById(oderId);
        if(!order.isPresent()){
            throw new NotFoundException();
        }
        Order orderget= order.get();
        if (!orderget.getAccountId().equals(ThreadContext.getCustomUserDetails().getId())){
            throw new DuplicateKeyException("ko co quyen truy cap");
        }
        return orderDetaillRepo.findAllByOrderId(oderId);
    }

}
