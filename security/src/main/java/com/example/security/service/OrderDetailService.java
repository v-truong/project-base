package com.example.security.service;

import com.example.security.dto.oder.CreateOderRequest;
import com.example.security.dto.orderdetail.CreateOrderDetailRequest;
import com.example.security.dto.orderdetail.UpdateOrderDetailRequest;
import com.example.security.entity.OrderDetail;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> getByOderId(String oderId) throws ChangeSetPersister.NotFoundException;
    String create(CreateOrderDetailRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, ChangeSetPersister.NotFoundException;
    String update(String id,UpdateOrderDetailRequest request) throws ChangeSetPersister.NotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException;

}
