package com.example.security.service.Implement;

import com.example.common.config.Constants;
import com.example.common.model.ThreadContext;
import com.example.security.dto.category.CreateCategoryRequest;
import com.example.security.dto.category.UpdateCategoryRequest;
import com.example.security.entity.Category;
import com.example.security.entity.Store;
import com.example.security.entity.Technical;
import com.example.security.repo.CategoryRepo;
import com.example.security.repo.CategoryTechnicalRepo;
import com.example.security.repo.StoreRepo;
import com.example.security.repo.TechnicalRepo;
import com.example.security.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CategoryImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private CategoryTechnicalRepo categoryTechnicalRepo;
    @Autowired
    private TechnicalRepo technicalRepo;
    @Autowired private StoreRepo storeRepo;

    //@Autowired private
    @Override
    public List<Category> getall() {

        return categoryRepo.findAllByIsDelete(Constants.ISDELETE_TRUE);
    }


    @Override
    public String create(CreateCategoryRequest request) {
        if (ThreadContext.getCustomUserDetails().getRole().equals(Constants.ROLE_SALESPERSON)) {
            Optional<Store> store =storeRepo.findById(request.getStoreId());
            if(!store.isPresent()){
                throw new DuplicateKeyException("store not fount");
            }
            Optional<Category> categoryOptional =categoryRepo.findById(request.getParentId());
            if (!categoryOptional.isPresent()){
                throw new DuplicateKeyException("store not fount");
            }
            if(categoryOptional.get().getParentId()!=null){
                throw new DuplicateKeyException("category not suitable");
            }
            List<Technical> technicals = technicalRepo.findByIdIn(request.getTechnicalId());
            Map<String, Technical> technicalMaps = technicals.stream().collect(Collectors.toMap(Technical::getId, Function.identity()));
            for (String technical : request.getTechnicalId()) {
                Technical technicalFor = technicalMaps.get(technical);
                if (technicalFor == null) {
                    throw new DuplicateKeyException(technicalFor.getId() + "does not exist");
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (String element : request.getTechnicalId()) {
                stringBuilder.append(element).append(",");
            }
            String listAsString = stringBuilder.toString();
            if (listAsString.length() > 0) {
                listAsString = listAsString.substring(0, listAsString.length() - 2);
            }
            System.out.println(listAsString);
            Category category = new Category();
            category.setName(request.getName());
            category.setTechnicalId(listAsString);
            categoryRepo.save(category);
            return "Success";
        }
        if(ThreadContext.getCustomUserDetails().getRole().equals(Constants.ROLE_ADMIN)){
            Category category =new Category();
            category.setName(request.getName());
            categoryRepo.save(category);
            return "Success";
        }
//        if(request.getTechnicalId()!=null){
//            Optional<Technical> technical =technicalRepo.findById(request.getTechnicalId());
//            if(!technical.isPresent()){
//                throw new DuplicateKeyException("TechnicalId not fount");
//            }
//            Category category =new Category();
//            CategoryTechnical categoryTechnical=new CategoryTechnical();
//            category.setName(request.getName());
//            category.setCreatedUser(ThreadContext.getCustomUserDetails().getUsername());
//            categoryRepo.save(category);
//            Optional<Category> categoryOptional=categoryRepo.findByName(request.getName());
//            if (!categoryOptional.isPresent()) {
//                throw new DuplicateKeyException("category not fount");
//            }
//            Category categoryget=categoryOptional.get();
//
//            categoryTechnical.setCategoryId(categoryget.getId());
//            categoryTechnical.setCreatedUser(ThreadContext.getCustomUserDetails().getUsername());
//            categoryTechnical.setTechnicalId(request.getTechnicalId());
//            categoryTechnicalRepo.save(categoryTechnical);
//            return "Success";
//        }
//        Category category =new Category();
//        category.setCreatedUser(ThreadContext.getCustomUserDetails().getUsername());
//        category.setName(request.getName());
//        categoryRepo.save(category);
        return "Success";
    }

    @Override
    public List<String> findTechnicallCategoryId(String categoryid) {

        return null;
    }

    @Override
    public String update(UpdateCategoryRequest request) {
        Optional<Category> categoryOptional =categoryRepo.findById(request.getId());
        if(!categoryOptional.isPresent()){
            throw new DuplicateKeyException(request+"id not fount");
        }
        Category categoryget=categoryOptional.get();
        Optional<Store> storeOptional=storeRepo.findById(categoryget.getStoreId());
        Store storegget=storeOptional.get();
//        if
//
//        if(ThreadContext.getCustomUserDetails().getId())
        categoryget.setName(request.getName());
        categoryget.getParentId();
        return null;
    }

}
