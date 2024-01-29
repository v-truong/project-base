package com.example.security.service.Implement;

import com.example.common.config.Constants;
import com.example.common.model.ThreadContext;
import com.example.common.util.SearchUtil;
import com.example.security.dto.product.CreateProductRequest;
import com.example.security.dto.product.SearchProductRequest;
import com.example.security.dto.product.UpdateProductRequest;
import com.example.security.entity.Category;
import com.example.security.entity.Product;
import com.example.security.entity.Store;
import com.example.security.repo.CategoryRepo;
import com.example.security.repo.ProductRepo;
import com.example.security.repo.StoreRepo;
import com.example.security.service.ProductService;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProductImpl implements ProductService{
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    StoreRepo storeRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Override
    public List<Product> getallProduct() { 
        // List<Product> product=productRepo.findAllbyIsDelete(Constants.isDeleteTrue);
       throw new UnsupportedOperationException("Unimplemented method 'createProduct'");
    }

    @Override
    public Product getProductById(String id) throws NotFoundException {
        Optional<Product> product =productRepo.findById(id);
        if (!product.isPresent()) {
            throw new NotFoundException();
        }
        if (product.get().getIsDelete()!=1) {
            throw new NotFoundException();
        }
        Product productGet=product.get();
        return productGet;
    }

    @Override
    public Product createProduct(CreateProductRequest request) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if(!ThreadContext.getCustomUserDetails().getRole().equals(Constants.ROLE_SALESPERSON)){
            throw new AccessDeniedException("acessDenied");
        }
        Optional<Store> store=storeRepo.findById(request.getStoreId());
        if(!store.isPresent()){
            throw new DuplicateKeyException("store not fount");
        }
        Optional<Category> category =categoryRepo.findById(request.getCategoryId());
        if(!category.isPresent()) {
            throw new DuplicateKeyException("Category Not fount");
        }
        Category categoryGet=category.get();

        Product product =new Product();
        PropertyUtils.copyProperties(product,request);
        product.setCategoryParentId(categoryGet.getParentId());
        return productRepo.save(product);
    }

    @Override
    public String deleteByIdProduct(List<String> ids) throws NotFoundException {
        if(!Constants.ROLE_SALESPERSON.equals(ThreadContext.getCustomUserDetails().getRole())){
            throw new AccessDeniedException("ko co quyen try cap");
        }
        List<Product> productList=new ArrayList<>();
        List<Product> product =productRepo.findByIdIn(ids);
        Map<String, Product> categoryMaps = product.stream().collect(Collectors.toMap(Product::getId, Function.identity()));

        for (String brand: ids) {
            Product productFor=categoryMaps.get(brand);
            if(productFor==null){
                throw new DuplicateKeyException(brand+":  khong ton tai");
            }
            productFor.setIsDelete(Constants.ISDELETE_FALSE);
            productList.add(productFor);
        }
        productRepo.saveAll(productList);
        return "Success";
    }

    @Override
    public Page<Product> advanceSearch(String filter, SearchProductRequest searchProductRequest, Pageable pageable) {
        if (searchProductRequest != null) {
            List<Specification<Product>> specList = getAdvanceSearchSpecList(searchProductRequest);
            if (filter != null && !filter.isEmpty()) {
                specList.add(SearchUtil.like("fullTextSearch", "%" + filter + "%"));
            }
            if (specList.size() > 0) {
                Specification<Product> spec = specList.get(0);
                for (int i = 1; i < specList.size(); i++) {
                    spec = spec.and(specList.get(i));

                }
                return productRepo.findAll(spec,pageable);
            }
        }
        return productRepo.findAll(pageable);
    }

    @Override
    public Product update(String id, UpdateProductRequest request) throws NotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Optional<Product> product =productRepo.findById(id);
        if(!product.isPresent()){
            throw new NotFoundException();
        }
        Product productGet=product.get();
        PropertyUtils.copyProperties(productGet,request);
        productRepo.save(productGet);
        return null;
    }

    private List<Specification<Product>> getAdvanceSearchSpecList(SearchProductRequest s){
        List<Specification<Product>> speclít=new ArrayList<>();
        if(s.getName()!=null && !s.getName().isEmpty()){
            speclít.add(SearchUtil.like("name","%"+s.getName()+"%"));
        }
        if (s.getBrandId()!=null && !s.getBrandId().isEmpty()){
            speclít.add(SearchUtil.eq("brandId",s.getBrandId()));
        }
        if (s.getCategoryId()!=null && !s.getBrandId().isEmpty()){
            speclít.add(SearchUtil.eq("categoryId",s.getBrandId()));
        }
        if (s.getCategoryParentId()!=null && !s.getCategoryParentId().isEmpty()){
            speclít.add(SearchUtil.eq("categoryParenId",s.getCategoryParentId()));
        }


        return  speclít;
    }

    
}
