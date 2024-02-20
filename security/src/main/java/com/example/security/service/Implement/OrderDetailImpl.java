package com.example.security.service.Implement;

import com.example.common.model.ThreadContext;
import com.example.security.dto.orderdetail.CreateOrderDetailRequest;
import com.example.security.dto.orderdetail.UpdateOrderDetailRequest;
import com.example.security.entity.Order;
import com.example.security.entity.OrderDetail;
import com.example.security.entity.Product;
import com.example.security.repo.OrderDetaillRepo;
import com.example.security.repo.OrderRepo;
import com.example.security.repo.ProductRepo;
import com.example.security.service.OrderDetailService;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailImpl implements OrderDetailService {
    @Autowired private OrderDetaillRepo orderDetaillRepo;
    @Autowired private OrderRepo orderRepo;
    @Autowired private ProductRepo productRepo;
    @Override
    public List<OrderDetail> getByOderId(String oderId) throws NotFoundException {
        Optional<Order> order=orderRepo.findById(oderId);
        if(!order.isPresent()){
            throw new NotFoundException();
        }
        Order orderget= order.get();
        if (!orderget.getAccountId().equals(ThreadContext.getCustomUserDetails().getId())){
            throw new AccessDeniedException("ko co quyen truy cap");
        }
        return orderDetaillRepo.findAllByOrderId(oderId);
    }

    @Override
    public String create(CreateOrderDetailRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, NotFoundException {
        Optional<Order> order=orderRepo.findById(request.getOrderId());
        if (!order.isPresent()) {
            throw new NotFoundException();
        }
        Optional<Product> product=productRepo.findById(request.getProductId());
        if(!product.isPresent()){
            throw new NotFoundException();
        }
        OrderDetail orderDetail=new OrderDetail();
        PropertyUtils.copyProperties(orderDetail,request);
        orderDetaillRepo.save(orderDetail);
        return "Success";
    }

    @Override
    public String update(String id,UpdateOrderDetailRequest request) throws NotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Optional<OrderDetail> orderDetail=orderDetaillRepo.findById(id);
        if(!orderDetail.isPresent()){
            throw new NotFoundException();
        }
        Optional<Order> order=orderRepo.findById(request.getOrderId());
        if(!order.isPresent()){
            throw new NotFoundException();
        }
        Optional<Product> product=productRepo.findById(request.getProductId());
        if(!product.isPresent()){
            throw new NotFoundException();
        }
        OrderDetail orderDetailGet =orderDetail.get();
        PropertyUtils.copyProperties(orderDetailGet,request);
        orderDetaillRepo.save(orderDetailGet);
        return "Success";
    }


}
