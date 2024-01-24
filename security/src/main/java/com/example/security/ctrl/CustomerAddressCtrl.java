package com.example.security.ctrl;


import com.example.common.config.Constants;
import com.example.common.config.enums.SortOrderEnum;
import com.example.common.response.PageResponse;
import com.example.common.util.SearchUtil;
import com.example.security.dto.customer.CreateAddressRequest;
import com.example.security.dto.customer.SearchAddressRequest;
import com.example.security.dto.customer.SearchCustomerRequest;
import com.example.security.dto.customer.UpdateAddressRequest;
import com.example.security.entity.Customer;
import com.example.security.entity.CustomerAddress;
import com.example.security.service.CustomerAddressService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customeraddress")
public class CustomerAddressCtrl {
    @Autowired private
    CustomerAddressService customerAddressService;

    @PostMapping("/filter")
    public PageResponse<CustomerAddress> getDetail(@RequestParam(required = false) String filter,@RequestBody SearchAddressRequest searchAddressRequest,Pageable pageable){
        Page<CustomerAddress> pageData = customerAddressService.GetDetail(filter,searchAddressRequest, pageable,Constants.ISDELETE_TRUE,searchAddressRequest.getCustomerId());
        return new PageResponse(pageData);
    }
    @PostMapping("/update")
    public String updateAddress(@RequestBody UpdateAddressRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, NotFoundException {
        return customerAddressService.Edit(request);
    }
    @PostMapping("/create")
    public String createAddress(@RequestBody CreateAddressRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, NotFoundException {
        return customerAddressService.Create(request);
    }
    @PostMapping("/delete")
    public String createAddress(List<String> request){
        return customerAddressService.Delete(request);
    }
}
