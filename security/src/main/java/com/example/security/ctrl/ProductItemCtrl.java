package com.example.security.ctrl;

import com.example.security.dto.productitem.CreateProductItemRequest;
import com.example.security.dto.productitem.UpdateProductItemRequest;
import com.example.security.entity.ProductItem;
import com.example.security.service.ProductItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/productItem")
public class ProductItemCtrl {
    @Autowired private ProductItemService productItemService;
    @PostMapping("/create")
    public String create(@RequestBody CreateProductItemRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return productItemService.create(request);
    }
    @PostMapping("/update")
    public String update(@RequestParam String id, @RequestBody UpdateProductItemRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        return productItemService.update(id,request);
    }
    @PostMapping("/getByDetailProductId")
    public List<ProductItem> getByDetailProductId(@RequestBody String productId){
        return productItemService.getDetailByPoductId(productId);
    }
    @PostMapping("/delete")
    public String delete(@RequestBody List<String> ids ){
        return productItemService.delete(ids);
    }
}
