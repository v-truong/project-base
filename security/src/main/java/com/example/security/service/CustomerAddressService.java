package com.example.security.service;

import com.example.security.dto.customer.CreateAddressRequest;
import com.example.security.dto.customer.UpdateAddressRequest;
import com.example.security.entity.CustomerAddress;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface CustomerAddressService {
    List<CustomerAddress> GetDetail(String CustomerId,int isDelete);

    String Create(CreateAddressRequest address) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, ChangeSetPersister.NotFoundException;

    String Edit(UpdateAddressRequest address) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, ChangeSetPersister.NotFoundException;
    String Delete(List<String> addressIds);
}
