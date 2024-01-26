package com.example.common.response;

import it.avutils.jmapper.JMapper;
import org.springframework.data.domain.Page;

import java.util.List;

public class PageResponseMap<T, R> extends SuccessResponse {
    private List<R> records;
    private int totalRecords;
    private T filter;

    public PageResponseMap(Page<R> pageData, JMapper<T, R> mapper) {
    }

    // Constructors, getters, setters, and other methods

    // Example method to add a record to the page response
    public void addRecord(R record) {
        records.add(record);
    }

    // Example method to set the filter for the page response
    public void setFilter(T filter) {
        this.filter = filter;
    }


}
