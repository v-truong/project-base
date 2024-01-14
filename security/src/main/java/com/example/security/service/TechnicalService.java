package com.example.security.service;

import com.example.security.dto.technical.CreateTechnicalRequest;
import com.example.security.entity.Category;
import com.example.security.entity.Technical;

import java.util.List;

public interface TechnicalService {
    String createTechnical(CreateTechnicalRequest request);
    List<Technical> getall();
}
