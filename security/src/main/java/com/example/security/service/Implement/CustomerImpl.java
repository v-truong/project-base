package com.example.security.service.Implement;

import com.example.common.config.Constants;
import com.example.common.model.ThreadContext;
import com.example.common.util.SearchUtil;
import com.example.security.dto.customer.CreateCustomerRequest;
import com.example.security.dto.customer.SearchCustomerRequest;
import com.example.security.dto.customer.UpdateCustomerRequest;
import com.example.security.dto.product.SearchProductRequest;
import com.example.security.entity.Customer;
import com.example.security.entity.Product;
import com.example.security.repo.CustomerRepo;
import com.example.security.service.CustomerService;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerImpl implements CustomerService {
    @Autowired
    CustomerRepo customerRepo;

    @Override
    public Page<Customer> getList(String filter, SearchCustomerRequest searchCustomerRequest,Pageable pageable, int isDelete) {
        if(!ThreadContext.getCustomUserDetails().getRole().equals(Constants.ROLE_SALESPERSON)){
            throw new AccessDeniedException("api.error.API-008");
        }
        if (searchCustomerRequest != null) {
            List<Specification<Customer>> specList = getAdvanceSearchSpecList(searchCustomerRequest);
            if (filter != null && !filter.isEmpty()) {
                specList.add(SearchUtil.like("fullTextSearch", "%" + filter + "%"));
            }
            if (specList.size() > 0) {
                Specification<Customer> spec = specList.get(0);
                for (int i = 1; i < specList.size(); i++) {
                    spec = spec.and(specList.get(i));

                }
                return customerRepo.findAll(pageable);
            }
        }
        return customerRepo.findAll(pageable);
    }

    private List<Specification<Customer>> getAdvanceSearchSpecList(SearchCustomerRequest s){
        List<Specification<Customer>> speclist=new ArrayList<>();
        if(s.getFullname()!=null && !s.getFullname().isEmpty()){
            speclist.add(SearchUtil.like("fullName","%"+s.getFullname()+"%"));
        }
        if (s.getEmail()!=null && !s.getEmail().isEmpty()){
            speclist.add(SearchUtil.eq("email",s.getEmail()));
        }
        if (s.getPhone()!=null && !s.getPhone().isEmpty()){
            speclist.add(SearchUtil.eq("phone",s.getPhone()));
        }

        return  speclist;
    }

    @Override
    public Customer getById(String customerId) throws NotFoundException {
        Optional<Customer> customer = customerRepo.findById(customerId);
        if(!customer.isPresent()){
            throw new NotFoundException();
        }
        Customer customerGet = customer.get();
        return customerGet;
    }

    @Override
    public String create(CreateCustomerRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, NotFoundException {
        if(request.getFullname() == null  || request.getEmail() == null || request.getPhone() == null ){
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
