package com.example.security.service.Implement;

import com.example.common.config.Constants;
import com.example.common.model.ThreadContext;
import com.example.security.dto.customer.CreateCustomerRequest;
import com.example.security.dto.customer.UpdateCustomerRequest;
import com.example.security.entity.Customer;
import com.example.security.repo.CustomerRepo;
import com.example.security.service.CustomerService;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerImpl implements CustomerService {
    @Autowired
    CustomerRepo customerRepo;

    @Override
    public List<Customer> getList(int isDelete) {
        if(ThreadContext.getCustomUserDetails().getRole()!=Constants.ROLE_SALESPERSON){
            throw new AccessDeniedException("api.error.API-008");
        }
        return customerRepo.findAllByIsDelete(isDelete);
    }

    @Override
    public Customer getById(String customerId) {
        Optional<Customer> customer = customerRepo.findById(customerId);
        Customer customerGet = customer.get();
        return customerGet;
    }

    @Override
    public String create(CreateCustomerRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, NotFoundException {
        if(request.getName().isEmpty() || request.getEmail().isEmpty() || request.getPhone().isEmpty()){
            throw new NotFoundException();
        }
        Customer customer = new Customer();
        PropertyUtils.copyProperties(customer,request);
        customerRepo.save(customer);
        return "create success";
    }

    @Override
    public String edit(UpdateCustomerRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, NotFoundException {
        Optional<Customer> customer = customerRepo.findById(ThreadContext.getCustomUserDetails().getId());
        if(customer.isPresent()){
            throw new NotFoundException();
        }
        Customer customerGet = customer.get();
        PropertyUtils.copyProperties(customerGet,request);
        customerRepo.save(customerGet);

        return "update thanh cong";
    }

    @Override
    public String delete(List<String> Ids) {
        List<Customer> customers = customerRepo.findAllById(Ids);
        for (Customer customer : customers){
            customer.setIsDelete(Constants.ISDELETE_FALSE);
        }
        customerRepo.saveAll(customers);
        return "xoa thanh cong";
    }
}
