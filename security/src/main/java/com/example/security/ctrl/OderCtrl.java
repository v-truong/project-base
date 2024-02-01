package com.example.security.ctrl;

import com.example.security.dto.oder.CreateOderRequest;
import com.example.security.entity.Order;
import com.example.security.service.OrderService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/oder")
public class OderCtrl {
    @Autowired private OrderService orderService;
    @GetMapping("/getbyaccountid")
    public List<Order> getByAccountId(){
        return orderService.getByAccountId();
    }
    @PostMapping("/create")
    public String create(@RequestBody CreateOderRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return orderService.create(request);
    }
    @PostMapping("/approve")
    public String approve(@RequestBody String id) throws NotFoundException {
        return  orderService.approve(id);
    }
//    @PostMapping("/")

}
