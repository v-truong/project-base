package com.example.security.ctrl;

import com.example.security.dto.orderdetail.CreateOrderDetailRequest;
import com.example.security.dto.orderdetail.UpdateOrderDetailRequest;
import com.example.security.entity.OrderDetail;
import com.example.security.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orderDetail")
public class OrderDetailCtrl {
    @Autowired private OrderDetailService orderDetailService;
    @PostMapping("/create")
    public String create(@RequestBody CreateOrderDetailRequest request) throws ChangeSetPersister.NotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return orderDetailService.create(request);
    }
    @PostMapping("/update")
    public String update(@RequestParam String id, @RequestBody UpdateOrderDetailRequest request) throws ChangeSetPersister.NotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return orderDetailService.update(id,request);
    }
    @PostMapping("getById")
    public List<OrderDetail> getById(@RequestBody String id) throws ChangeSetPersister.NotFoundException {
        return orderDetailService.getByOderId(id);
    }
}
