package com.example.security.service;

import com.example.security.dto.customer.CreateAddressRequest;
import com.example.security.dto.customer.SearchAddressRequest;
import com.example.security.dto.customer.SearchCustomerRequest;
import com.example.security.dto.customer.UpdateAddressRequest;
import com.example.security.entity.CustomerAddress;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface CustomerAddressService {
    Page<CustomerAddress> GetDetail(String filter, SearchAddressRequest searchAddressRequest,Pageable pageable, int isDelete,String customerId);

    String Create(CreateAddressRequest address) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, ChangeSetPersister.NotFoundException;

    String Edit(UpdateAddressRequest address) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, ChangeSetPersister.NotFoundException;
    String Delete(List<String> addressIds);
}
