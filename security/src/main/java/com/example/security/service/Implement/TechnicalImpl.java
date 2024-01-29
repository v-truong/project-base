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
import org.springframework.security.access.AccessDeniedException;
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
            technical.setUnit(request.getUnit());
            technicalRepo.save(technical);
            return "Success";
        }
        throw new AccessDeniedException("");
    }

    @Override
    public List<Technical> getall() {
        return technicalRepo.findAllByIsDelete(Constants.ISDELETE_TRUE);
    }

    @Override
    public String Update(UpdateTechnicalRequest request) {
        Optional<Technical> technicalOptional =technicalRepo.findById(request.getId());
        if(!technicalOptional.isPresent()){
            throw new DuplicateKeyException(request+"id not fount");
        }
        Technical technical=technicalOptional.get();
        Optional<Store> storeOptional=storeRepo.findById(technical.getStoreId());
        Store storegget=storeOptional.get();
        boolean contains =storegget.getStaffIds().contains(ThreadContext.getCustomUserDetails().getId());
        if(!contains){
            throw new AccessDeniedException("Access");
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
        technical.setName(request.getName());
        technical.setCategoryId(listAsString);
        technical.setUnit(request.getUnit());
        technicalRepo.save(technical);

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

    @Override
    public String delete(List<String> ids) {
        if(!Constants.ROLE_SALESPERSON.equals(ThreadContext.getCustomUserDetails().getRole())){
            throw new AccessDeniedException("ko co quyen try cap");
        }
        List<Technical> technicalList=new ArrayList<>();
        List<Technical> technicals=technicalRepo.findAllById(ids);
        Map<String, Technical> technicalMaps = technicals.stream().collect(Collectors.toMap(Technical::getId, Function.identity()));
        for (String brand: ids) {
            Technical technicalFor=technicalMaps.get(brand);
            if(technicalFor==null){
                throw new DuplicateKeyException(brand+":  khong ton tai");
            }
            technicalFor.setIsDelete(Constants.ISDELETE_FALSE);
            technicalList.add(technicalFor);
        }
        technicalRepo.saveAll(technicalList);


        return null;
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