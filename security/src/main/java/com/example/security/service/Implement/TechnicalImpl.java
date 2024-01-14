package com.example.security.service.Implement;

import com.example.common.config.Constants;
import com.example.common.model.ThreadContext;
import com.example.security.dto.technical.CreateTechnicalRequest;
import com.example.security.entity.Category;
import com.example.security.entity.CategoryTechnical;
import com.example.security.entity.Technical;
import com.example.security.repo.CategoryRepo;
import com.example.security.repo.CategoryTechnicalRepo;
import com.example.security.repo.TechnicalRepo;
import com.example.security.service.TechnicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TechnicalImpl implements TechnicalService {
    @Autowired private TechnicalRepo technicalRepo;
    @Autowired private CategoryRepo categoryRepo;
    @Autowired private CategoryTechnicalRepo categoryTechnicalRepo;


    @Override
    public String createTechnical(CreateTechnicalRequest request) {
        if(!ThreadContext.getCustomUserDetails().getRole().equals(Constants.ROLE_SALESPERSON)){
            throw new AccessDeniedException("api.error.API-008");
        }
        Technical technical =new Technical();
        if (request.getCategoryId()!=null||!request.getCategoryId().isEmpty()){
            Optional<Category> category =categoryRepo.findById(request.getCategoryId());
            if (!category.isPresent()){
                throw new DuplicateKeyException("technical not fount");
            }
            technical.setName(request.getName());
            technical.setUnit(request.getUnit());
            technicalRepo.save(technical);
            Optional<Technical> technicalOptional =technicalRepo.findByName(request.getName());
            if(!technicalOptional.isPresent()) {
                throw new DuplicateKeyException("technical not fount");
            }
            Technical technicalget =technicalOptional.get();
            CategoryTechnical categoryTechnical =new CategoryTechnical();
            categoryTechnical.setTechnicalId(technicalget.getId());
            categoryTechnical.setCategoryId(request.getCategoryId());
            categoryTechnicalRepo.save(categoryTechnical);
        }
        technical.setName(request.getName());
        technical.setUnit(request.getUnit());
        technicalRepo.save(technical);
        return "Success";
    }

    @Override
    public List<Technical> getall() {
        return technicalRepo.findAllByIsDelete(Constants.ISDELETE_TRUE);
    }
}
