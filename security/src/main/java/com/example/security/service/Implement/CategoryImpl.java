package com.example.security.service.Implement;

import com.example.common.config.Constants;
import com.example.common.model.ThreadContext;
import com.example.security.dto.category.CreateCategoryRequest;
import com.example.security.entity.Category;
import com.example.security.entity.CategoryTechnical;
import com.example.security.entity.Technical;
import com.example.security.repo.CategoryRepo;
import com.example.security.repo.CategoryTechnicalRepo;
import com.example.security.repo.TechnicalRepo;
import com.example.security.service.CategoryService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryImpl implements CategoryService {
    @Autowired private CategoryRepo categoryRepo;
    @Autowired private CategoryTechnicalRepo categoryTechnicalRepo;
    @Autowired private TechnicalRepo technicalRepo;
    @Override
    public List<Category> getall() {
        return categoryRepo.findAllByIsDelete(Constants.ISDELETE_TRUE);
    }

    @Override
    public String create(CreateCategoryRequest request) {
        if (ThreadContext.getCustomUserDetails().getRole()!=Constants.ROLE_SALESPERSON){
            throw new AccessDeniedException("api.error.API-008");
        }
        if(request.getTechnicalId()!=null||!request.getTechnicalId().isEmpty()){
            Optional<Technical> technical =technicalRepo.findById(request.getTechnicalId());
            if(!technical.isPresent()){
                throw new DuplicateKeyException("TechnicalId not fount");
            }
            Category category =new Category();
            CategoryTechnical categoryTechnical=new CategoryTechnical();
            category.setName(request.getName());
            category.setCreatedUser(ThreadContext.getCustomUserDetails().getUsername());
            categoryRepo.save(category);
            Optional<Category> categoryOptional=categoryRepo.findByName(request.getName());
            if (!categoryOptional.isPresent()) {
                throw new DuplicateKeyException("category not fount");
            }
            Category categoryget=categoryOptional.get();

            categoryTechnical.setCategoryId(categoryget.getId());
            categoryTechnical.setCreatedUser(ThreadContext.getCustomUserDetails().getUsername());
            categoryTechnical.setTechnicalId(request.getTechnicalId());
            categoryTechnicalRepo.save(categoryTechnical);
            return "Success";
        }
        Category category =new Category();
        category.setCreatedUser(ThreadContext.getCustomUserDetails().getUsername());
        category.setName(request.getName());
        categoryRepo.save(category);
        return null;
    }

    @Override
    public List<String> findTechnicallCategoryId(String categoryid) {

        return null;
    }

}
