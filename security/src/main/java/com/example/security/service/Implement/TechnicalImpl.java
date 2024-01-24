package com.example.security.service.Implement;

import com.example.common.config.Constants;
import com.example.common.model.ThreadContext;
import com.example.common.util.SearchUtil;

import com.example.security.dto.technical.CreateTechnicalRequest;
import com.example.security.dto.technical.SearchTechnicalRequest;
import com.example.security.dto.technical.UpdateTechnicalRequest;
import com.example.security.entity.*;
import com.example.security.repo.CategoryRepo;
import com.example.security.repo.CategoryTechnicalRepo;
import com.example.security.repo.StoreRepo;
import com.example.security.repo.TechnicalRepo;
import com.example.security.service.TechnicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TechnicalImpl implements TechnicalService {
    @Autowired private TechnicalRepo technicalRepo;
    @Autowired private CategoryRepo categoryRepo;
    @Autowired private CategoryTechnicalRepo categoryTechnicalRepo;
    @Autowired private StoreRepo storeRepo;



    @Override
    public String createTechnical(CreateTechnicalRequest request) {
        if (ThreadContext.getCustomUserDetails().getRole().equals(Constants.ROLE_SALESPERSON)) {
            Optional<Store> store =storeRepo.findById(request.getStoreId());
            if(!store.isPresent()){
                throw new DuplicateKeyException("store not fount");
            }
            List<Category> categoryLs =categoryRepo.findByIdIn(request.getCategoryId());
            Map<String, Category> categoryMap = categoryLs.stream().collect(Collectors.toMap(Category::getId, Function.identity()));
            for (String category : request.getCategoryId()) {
                Category categoryFor = categoryMap.get(category);
                if (categoryFor == null) {
                    throw new DuplicateKeyException(categoryFor.getId() + "does not exist");
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (String element : request.getCategoryId()) {
                stringBuilder.append(element).append(",");
            }
            String listAsString = stringBuilder.toString();
            if (listAsString.length() > 0) {
                listAsString = listAsString.substring(0, listAsString.length() - 2);
            }
            System.out.println(listAsString);
            Technical technical = new Technical();
            technical.setName(request.getName());
            technical.setCategoryId(listAsString);
            technicalRepo.save(technical);
            return "Success";
        }
//        Technical technical =new Technical();
//        if (request.getCategoryId()!=null||!request.getCategoryId().isEmpty()){
//            Optional<Category> category =categoryRepo.findById(request.getCategoryId());
//            if (!category.isPresent()){
//                throw new DuplicateKeyException("technical not fount");
//            }
//            technical.setName(request.getName());
//            technical.setUnit(request.getUnit());
//            technicalRepo.save(technical);
//            Optional<Technical> technicalOptional =technicalRepo.findByName(request.getName());
//            if(!technicalOptional.isPresent()) {
//                throw new DuplicateKeyException("technical not fount");
//            }
//            Technical technicalget =technicalOptional.get();
//            CategoryTechnical categoryTechnical =new CategoryTechnical();
//            categoryTechnical.setTechnicalId(technicalget.getId());
//            categoryTechnical.setCategoryId(request.getCategoryId());
//            categoryTechnicalRepo.save(categoryTechnical);
//        }
//        technical.setName(request.getName());
//        technical.setUnit(request.getUnit());
//        technicalRepo.save(technical);
        return "Success";
    }

    @Override
    public List<Technical> getall() {
        return technicalRepo.findAllByIsDelete(Constants.ISDELETE_TRUE);
    }

    @Override
    public String Update(UpdateTechnicalRequest technicalRequest) {

        return null;
    }

    @Override
    public Page<Technical> advanceSearch(String filter, SearchTechnicalRequest searchTechnicalRequest, Pageable pageable) {
        if (searchTechnicalRequest != null) {
            List<Specification<Technical>> specList = getAdvanceSearchSpecList(searchTechnicalRequest);
            if (filter != null && !filter.isEmpty()) {
                specList.add(SearchUtil.like("fullTextSearch", "%" + filter + "%"));
            }
            if (specList.size() > 0) {
                Specification<Technical> spec = specList.get(0);
                for (int i = 1; i < specList.size(); i++) {
                    spec = spec.and(specList.get(i));

                }
                return technicalRepo.findAll(spec,pageable);
            }
        }
        return technicalRepo.findAll(pageable);
    }
    private List<Specification<Technical>> getAdvanceSearchSpecList(SearchTechnicalRequest s){
        List<Specification<Technical>> speclít=new ArrayList<>();
        if(s.getName()!=null && !s.getName().isEmpty()){
            speclít.add(SearchUtil.like("name","%"+s.getName()+"%"));
        }
        if (s.getUnit()!=null && !s.getUnit().isEmpty()){
            speclít.add(SearchUtil.like("unit","%"+s.getUnit()+"%"));
        }
        if (s.getStoreId()!=null && !s.getStoreId().isEmpty()){
            speclít.add(SearchUtil.eq("storeId",s.getStoreId()));
        }

        return  speclít;
    }

}