package com.example.security.service;

import com.example.security.dto.product.SearchProductRequest;
import com.example.security.dto.technical.CreateTechnicalRequest;
import com.example.security.dto.technical.SearchTechnicalRequest;
import com.example.security.dto.technical.UpdateTechnicalRequest;
import com.example.security.entity.Category;
import com.example.security.entity.Product;
import com.example.security.entity.Technical;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TechnicalService {
    String createTechnical(CreateTechnicalRequest request);
    List<Technical> getall();
    String Update(UpdateTechnicalRequest technicalRequest);
    Page<Technical> advanceSearch(String filter, SearchTechnicalRequest searchTechnicalRequest, Pageable pageable);
    String delete(List<String> ids);
}
