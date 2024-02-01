package com.example.security.service.Implement;

import com.example.common.config.Constants;
import com.example.common.model.ThreadContext;
import com.example.security.dto.productitem.CreateProductItemRequest;
import com.example.security.dto.productitem.UpdateProductItemRequest;
import com.example.security.entity.Product;
import com.example.security.entity.ProductItem;
import com.example.security.entity.Store;
import com.example.security.entity.Technical;
import com.example.security.repo.ProductItemRepo;
import com.example.security.repo.ProductRepo;
import com.example.security.repo.StoreRepo;
import com.example.security.repo.TechnicalRepo;
import com.example.security.service.ProductItemService;
import javassist.NotFoundException;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.rmi.AccessException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductItemImpl implements ProductItemService {
    @Autowired
    private ProductItemRepo productItemRepo;
    @Autowired
    private StoreRepo storeRepo;
    @Autowired
    private TechnicalRepo technicalRepo;
    @Autowired
    private ProductRepo productRepo;
    @Override
    public String create(CreateProductItemRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Optional<Store> store=storeRepo.findById(request.getStoreId());
        if(!store.isPresent()){
            throw new DuplicateKeyException("store not fount");
        }
        Store storeget=store.get();
        boolean storeIn=storeget.getStaffIds().contains(ThreadContext.getCustomUserDetails().getId());
        if(!storeIn){
            throw new AccessDeniedException("khon co quyen truy cap");
        }
        Optional<Technical> technical =technicalRepo.findById(request.getTechnicalId());
        if(!technical.isPresent()){
            throw new DuplicateKeyException("technical not fount");
        }
        Optional<Product> product =productRepo.findById(request.getProductId());
        if(!product.isPresent()){
            throw new DuplicateKeyException("technical not fount");
        }
        ProductItem productItem =new ProductItem();
        PropertyUtils.copyProperties(productItem,request);
        productItemRepo.save(productItem);

        return "Success";
    }

    @Override
    public String delete(List<String> ids) {
        List<ProductItem> productItemList=new ArrayList<>();
        List<ProductItem> product =productItemRepo.findByIdIn(ids);
        Map<String, ProductItem> ProductItemMaps = product.stream().collect(Collectors.toMap(ProductItem::getId, Function.identity()));

        for (String brand: ids) {
            ProductItem productFor=ProductItemMaps.get(brand);
            if(productFor==null){
                throw new DuplicateKeyException(brand+":  khong ton tai");
            }
            productFor.setIsDelete(Constants.ISDELETE_FALSE);
            productItemList.add(productFor);
        }
        productItemRepo.saveAll(productItemList);

        return "Success";
    }

    @Override
    public List<ProductItem> getDetailByPoductId(String productId) {
        List<ProductItem> productItemList=productItemRepo.findAllByProductIdAndIsDelete(productId,Constants.ISDELETE_TRUE);
        return productItemList;
    }

    @Override
    public String update(String id , UpdateProductItemRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Optional<ProductItem> productItemOptional =productItemRepo.findById(id);
        if(!productItemOptional.isPresent()){
            throw new DuplicateKeyException("not fount");
        }
        Optional<Product> product =productRepo.findById(request.getProductId());
        if(!product.isPresent()){
            throw new DuplicateKeyException("productId not fount");
        }
        Optional<Technical> technical =technicalRepo.findById(request.getTechnicalId());
        if(!technical.isPresent()){
            throw new DuplicateKeyException("technical not fount");
        }
        ProductItem productItem=productItemOptional.get();
        PropertyUtils.copyProperties(productItem,request);
        return null;
    }
}
