package com.example.security.service.Implement;

import com.example.common.config.Constants;
import com.example.common.model.ThreadContext;
import com.example.security.dto.customer.CreateAddressRequest;
import com.example.security.dto.customer.UpdateAddressRequest;
import com.example.security.entity.Customer;
import com.example.security.entity.CustomerAddress;
import com.example.security.repo.CustomerAddressRepo;
import com.example.security.service.CustomerAddressService;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerAddressImpl implements CustomerAddressService {
    @Autowired
    private  CustomerAddressRepo customerAddressRepo;

    @Override
    public List<CustomerAddress> GetDetail(String CustomerId,int isDelete) {
        List<CustomerAddress> Addresses = customerAddressRepo.findAllByCustomerIdAndIsDelete(CustomerId,isDelete);
        return Addresses;
    }

    @Override
    public String Create(CreateAddressRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if(request.getProvinceId().isEmpty() || request.getDistrictId().isEmpty() || request.getWardId().isEmpty()){
            return "Phai dien vao required input";
        }
        CustomerAddress address = new CustomerAddress();
        PropertyUtils.copyProperties(address,request);
        customerAddressRepo.save(address);
        return "create success";
    }

    @Override
    public String Edit(UpdateAddressRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Optional<CustomerAddress> address = customerAddressRepo.findById(ThreadContext.getCustomUserDetails().getId());
        if(address.isPresent()){
            return "khong tim thay address";
        }
        CustomerAddress addressGet = address.get();
        PropertyUtils.copyProperties(addressGet,request);
        customerAddressRepo.save(addressGet);

        return "update thanh cong";
    }

    @Override
    public String Delete(List<String> addressIds) {
        List<CustomerAddress> addresses = customerAddressRepo.findAllById(addressIds);
        for (CustomerAddress address : addresses){
            address.setIsDelete(Constants.ISDELETE_FALSE);
        }
        customerAddressRepo.saveAll(addresses);
        return "xoa thanh cong";
    }
}
