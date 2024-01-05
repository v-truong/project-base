package com.example.security.ctrl;

import com.example.security.entity.Order;
import com.example.security.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/oder")
public class OderCtrl {
    @Autowired private OrderService orderService;
    @GetMapping("/getbyaccountid")
    public List<Order> getByAccountId(){
        return orderService.getByAccountId();
    }

}
