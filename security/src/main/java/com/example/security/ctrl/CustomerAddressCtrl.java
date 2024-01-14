package com.example.security.ctrl;


import com.example.common.config.Constants;
import com.example.security.dto.customer.CreateAddressRequest;
import com.example.security.dto.customer.UpdateAddressRequest;
import com.example.security.entity.Customer;
import com.example.security.entity.CustomerAddress;
import com.example.security.service.CustomerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customeraddress")
public class CustomerAddressCtrl {
    @Autowired private
    CustomerAddressService customerAddressService;

    @GetMapping("/getDetail")
<<<<<<< HEAD
    public List<CustomerAddress> getDetail(@RequestBody CustomerAddress request){
        return customerAddressService.GetDetail(request.getCustomerId(),Constants.ISDELETE_TRUE);
=======
    public List<CustomerAddress> getDetail(@RequestBody Customer Customer){
        return customerAddressService.GetDetail(Customer.getId(),Constants.ISDELETE_TRUE);
>>>>>>> 34fa0d035c8ee74f23fd34a2d71737dd443734b6
    }
    @PostMapping("/updateAddress")
    public String updateAddress(@RequestBody UpdateAddressRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, NotFoundException {
        return customerAddressService.Edit(request);
    }
    @PostMapping("/createAddress")
    public String createAddress(@RequestBody CreateAddressRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, NotFoundException {
        return customerAddressService.Create(request);
    }
    @PostMapping("/deleteAddress")
    public String createAddress(List<String> request){
        return customerAddressService.Delete(request);
    }
}
