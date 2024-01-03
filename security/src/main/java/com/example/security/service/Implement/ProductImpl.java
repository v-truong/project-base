package com.example.security.service.Implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.common.util.SearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;

import com.example.security.dto.product.SearchProductRequest;
import com.example.security.entity.Product;
import com.example.security.entity.Provider;
import com.example.security.repo.ProductRepo;
import com.example.security.service.ProductService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ProductImpl implements ProductService{
    @Autowired
    private ProductRepo productRepo;
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
    public Boolean createProduct() {
        

        throw new UnsupportedOperationException("Unimplemented method 'createProduct'");
    }

    @Override
    public Boolean deleteByIdProduct(String id) throws NotFoundException {
        Optional<Product> product =productRepo.findById(id);

        if (!product.isPresent()) {
            throw new NotFoundException();
        }
        Product productGet=product.get();
        productGet.setIsDelete(1);
        productRepo.save(productGet);
       return true;
    }

    @Override
    public Page<Provider> advanceSearch(String filter, SearchProductRequest searchProductRequest, Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'advanceSearch'");
    }
    private List<Specification<Product>> getAdvanceSearchSpecList(SearchProductRequest s){
        List<Specification<Product>> speclít=new ArrayList<>();
        if(s.getName()!=null && !s.getName().isEmpty()){
            speclít.add(SearchUtil.like("name","%"+s.getName()+"%"));
        }
        if (s.getBrandId()!=null && !s.getBrandId().isEmpty()){

        }
        return  speclít;
    }

    
}
