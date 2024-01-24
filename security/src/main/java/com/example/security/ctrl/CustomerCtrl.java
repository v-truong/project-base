package com.example.security.ctrl;

import com.example.common.config.Constants;
import com.example.common.config.enums.SortOrderEnum;
import com.example.common.response.PageResponse;
import com.example.common.util.SearchUtil;
import com.example.security.dto.customer.CreateCustomerRequest;
import com.example.security.dto.customer.SearchCustomerRequest;
import com.example.security.dto.customer.UpdateCustomerRequest;
import com.example.security.dto.product.SearchProductRequest;
import com.example.security.entity.Customer;
import com.example.security.entity.Product;
import com.example.security.service.CustomerService;
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
@RequestMapping("/api/v1/customer")
public class CustomerCtrl {
    @Autowired
    CustomerService customerService;

    @PostMapping("/detail")
    public Customer getDetail(@RequestBody Customer request){
        return customerService.getById(request.getId());
    }
    @PostMapping("/filter")
    public PageResponse<Customer> getDetail(@RequestParam(required = false) String filter, @Valid @RequestBody SearchCustomerRequest searchRequest,
                                            @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer page,
                                            @Positive @RequestParam(required = false) Integer size, @RequestParam(required = false) String sort,
                                            @RequestParam(required = false) SortOrderEnum order){
        Pageable pageable = SearchUtil.getPageableFromParam(page, size, sort, order);
        Page<Customer> pageData = customerService.getList(filter, searchRequest, pageable,Constants.ISDELETE_TRUE);
        return new PageResponse(pageData);
    }
    @PostMapping("/update")
    public String updateAddress(@RequestBody UpdateCustomerRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, NotFoundException {
        return customerService.edit(request);
    }
    @PostMapping("/create")
    public String createAddress(@RequestBody CreateCustomerRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, NotFoundException {
        return customerService.create(request);
    }
    @PostMapping("/delete")
    public String createAddress(List<String> request){
        return customerService.delete(request);
    }
}
