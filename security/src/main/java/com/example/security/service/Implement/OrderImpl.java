package com.example.security.service.Implement;

import com.example.common.config.Constants;
import com.example.common.model.ThreadContext;
import com.example.security.dto.oder.CreateOderRequest;
import com.example.security.entity.Order;
import com.example.security.entity.OrderHistory;
import com.example.security.entity.Store;
import com.example.security.repo.OrderHistoryRepo;
import com.example.security.repo.OrderRepo;
import com.example.security.repo.StoreRepo;
import com.example.security.service.OrderService;
import javassist.NotFoundException;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
public class OrderImpl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private StoreRepo storeRepo;
    @Autowired
    private OrderHistoryRepo orderHistoryRepo;

    @Override
    public List<Order> getByAccountId() {

        return orderRepo.findAllByAccountId(ThreadContext.getCustomUserDetails().getId());
    }

    @Override
    public String create(CreateOderRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Order order = new Order();
        PropertyUtils.copyProperties(order, request);
        Order oderSave = orderRepo.save(order);
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setOderId(oderSave.getId());
        orderHistory.setAccountId(oderSave.getAccountId());
        orderHistory.setProductId(ThreadContext.getCustomUserDetails().getId());
        orderHistoryRepo.save(orderHistory);
        return "Success";
    }

    @Override
    public String update() {
        return null;
    }

    @Override
    public String approve(String id) throws NotFoundException {
        Optional<Order> orderOptional = orderRepo.findById(id);
        if (!orderOptional.isPresent()) {
            throw new NotFoundException("");
        }
        Order order = orderOptional.get();
        Optional<Store> storeOptional = storeRepo.findById(order.getStoreId());
        if (!storeOptional.isPresent()) {
            throw new NotFoundException("");
        }
        Store store = storeOptional.get();
        boolean checkStaffIds = store.getStaffIds().contains(ThreadContext.getCustomUserDetails().getId());
        if (order.getStatus().equals(Constants.TYPE_STORE_CREATED) && ThreadContext.getCustomUserDetails().getRole().equals(Constants.ROLE_SALESPERSON) && checkStaffIds) {
            order.setStatus(Constants.ORDER_STATUS_RECEIVED);
            orderRepo.save(order);
            return "Success";
        }
        if (order.getStatus().equals(Constants.ORDER_STATUS_RECEIVED) && ThreadContext.getCustomUserDetails().getRole().equals(Constants.ROLE_SALESPERSON) && checkStaffIds) {
            order.setStatus(Constants.ORDER_STATUS_PACKAGING);
            orderRepo.save(order);
            return "Success";
        }
        if (order.getStatus().equals(Constants.ORDER_STATUS_PACKAGING) && ThreadContext.getCustomUserDetails().getRole().equals(Constants.ROLE_SALESPERSON) && checkStaffIds) {
            order.setStatus(Constants.ORDER_STATUS_SHIPPING);
            orderRepo.save(order);
            return "Success";
        }
        if (order.getStatus().equals(Constants.ORDER_STATUS_SHIPPING) && ThreadContext.getCustomUserDetails().getRole().equals(Constants.ROLE_SALESPERSON) && checkStaffIds) {
            order.setStatus(Constants.ORDER_STATUS_PACKAGING);
            orderRepo.save(order);
            return "Success";
        }
        return null;
    }
}
