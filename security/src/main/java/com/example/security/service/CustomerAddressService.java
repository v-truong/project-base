package com.example.security.service;

import com.example.security.dto.customer.CreateAddressRequest;
import com.example.security.dto.customer.UpdateAddressRequest;
import com.example.security.entity.CustomerAddress;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface CustomerAddressService {
    List<CustomerAddress> GetDetail(String CustomerId,int isDelete);

    String Create(CreateAddressRequest address) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;

    String Edit(UpdateAddressRequest address) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;
    String Delete(List<String> addressIds);
}
