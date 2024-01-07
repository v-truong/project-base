package com.example.security.ctrl;

import com.example.common.config.Constants;
import com.example.security.dto.customer.CreateAddressRequest;
import com.example.security.dto.customer.CreateCustomerRequest;
import com.example.security.dto.customer.UpdateAddressRequest;
import com.example.security.dto.customer.UpdateCustomerRequest;
import com.example.security.entity.Customer;
import com.example.security.entity.CustomerAddress;
import com.example.security.service.CustomerAddressService;
import com.example.security.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerCtrl {
    @Autowired
    CustomerService customerService;

    @GetMapping("/getDetail/{cid}")
    public Customer getDetail(@PathVariable String CustomerId){
        return customerService.getById(CustomerId);
    }
    @GetMapping("/getDetail/")
    public List<Customer> getDetail(){
        return customerService.getList(Constants.ISDELETE_TRUE);
    }
    @PostMapping("/updateCustomer/")
    public String updateAddress(@RequestBody UpdateCustomerRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return customerService.edit(request);
    }
    @PostMapping("/createCustomer/")
    public String createAddress(@RequestBody CreateCustomerRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return customerService.create(request);
    }
    @PostMapping("/deleteCustomer/")
    public String createAddress(List<String> request){
        return customerService.delete(request);
    }
}
