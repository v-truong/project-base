package com.example.security.service.Implement;

import com.example.common.config.Constants;
import com.example.common.model.ThreadContext;
import com.example.common.util.SearchUtil;
import com.example.security.dto.customer.CreateAddressRequest;
import com.example.security.dto.customer.SearchAddressRequest;
import com.example.security.dto.customer.SearchCustomerRequest;
import com.example.security.dto.customer.UpdateAddressRequest;
import com.example.security.entity.Customer;
import com.example.security.entity.CustomerAddress;
import com.example.security.repo.CustomerAddressRepo;
import com.example.security.service.CustomerAddressService;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerAddressImpl implements CustomerAddressService {
    @Autowired
    private  CustomerAddressRepo customerAddressRepo;

    @Override
    public Page<CustomerAddress> GetDetail(String filter, SearchAddressRequest searchAddressRequest, Pageable pageable, String CustomerId, int isDelete) {
        if (searchAddressRequest != null) {
            List<Specification<CustomerAddress>> specList = getAdvanceSearchSpecList(searchAddressRequest);
            if (filter != null && !filter.isEmpty()) {
                specList.add(SearchUtil.like("fullTextSearch", "%" + filter + "%"));
            }
            if (specList.size() > 0) {
                Specification<CustomerAddress> spec = specList.get(0);
                for (int i = 1; i < specList.size(); i++) {
                    spec = spec.and(specList.get(i));

                }
                return customerAddressRepo.findAllByCustomerIdAndIsDelete(pageable,specList,CustomerId,isDelete);
            }
        }
        return customerAddressRepo.findAll(pageable);
    }

    private List<Specification<CustomerAddress>> getAdvanceSearchSpecList(SearchAddressRequest s){
        List<Specification<CustomerAddress>> speclist=new ArrayList<>();
        if(s.getFullAddress()!=null && !s.getFullAddress().isEmpty()){
            speclist.add(SearchUtil.like("fullAddress","%"+s.getFullAddress()+"%"));
        }
        if (s.getProvinceCode()!=null && !s.getProvinceCode().isEmpty()){
            speclist.add(SearchUtil.eq("province",s.getProvinceCode()));
        }
        if (s.getDistrictCode()!=null && !s.getDistrictCode().isEmpty()){
            speclist.add(SearchUtil.eq("district",s.getDistrictCode()));
        }
        if (s.getWardCode()!=null && !s.getWardCode().isEmpty()){
            speclist.add(SearchUtil.eq("ward",s.getWardCode()));
        }

        return  speclist;
    }

    @Override
    public String Create(CreateAddressRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, NotFoundException {

        if(request.getProvinceCode() == null || request.getDistrictCode() == null  || request.getWardCode() == null ){
            throw new NotFoundException();
        }

        CustomerAddress address = new CustomerAddress();
        PropertyUtils.copyProperties(address,request);
        customerAddressRepo.save(address);
        return "create success";
    }

    @Override
    public String Edit(UpdateAddressRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, NotFoundException {
        Optional<CustomerAddress> address = customerAddressRepo.findById(ThreadContext.getCustomUserDetails().getId());
        if(address.isPresent()){
            throw new NotFoundException();
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
