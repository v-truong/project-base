package com.example.security.service;

import com.example.security.entity.OrderDetail;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> getByOderid(String oderId) throws ChangeSetPersister.NotFoundException;

}
