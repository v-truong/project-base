package com.example.security.repo;

import com.example.security.entity.CategoryTechnical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryTechnicalRepo extends JpaRepository<CategoryTechnical,String> {
    List<CategoryTechnical> findAllByCategoryIdAndIsDelete(String catedoryId,int isdelete);

}
