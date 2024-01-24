package com.example.security.ctrl;

import com.example.common.config.enums.SortOrderEnum;
import com.example.common.response.PageResponse;
import com.example.common.util.SearchUtil;
import com.example.security.dto.product.SearchProductRequest;
import com.example.security.dto.technical.CreateTechnicalRequest;
import com.example.security.dto.technical.SearchTechnicalRequest;
import com.example.security.entity.Product;
import com.example.security.entity.Technical;
import com.example.security.service.TechnicalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/technical")
public class TechnicalCtrl {
    @Autowired private TechnicalService technicalService;
    @PostMapping("/create")
    public String createTechnical(@RequestBody CreateTechnicalRequest request){
        return technicalService.createTechnical(request);
    }
    @PostMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<Technical> advanceSearch(@RequestParam(required = false) String filter, @Valid @RequestBody SearchTechnicalRequest searchRequest,
                                                 @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer page,
                                                 @Positive @RequestParam(required = false) Integer size, @RequestParam(required = false) String sort,
                                                 @RequestParam(required = false) SortOrderEnum order){
        Pageable pageable = SearchUtil.getPageableFromParam(page, size, sort, order);
        Page<Technical> pageData = technicalService.advanceSearch(filter, searchRequest, pageable);
        return new PageResponse<>(pageData);
    }

}
