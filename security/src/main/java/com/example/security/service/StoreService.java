package com.example.security.service;

import com.example.security.dto.store.CreateStoreRequest;
import com.example.security.dto.store.FilterStoreRequest;
import com.example.security.dto.store.UpdateStoreRequest;
import com.example.security.entity.Store;

import java.util.List;

public interface StoreService {
    List<Store> filter(FilterStoreRequest request);
    List<Store> getAllOwnerId();
    List<Store> getAllByStaffId();
    String create(CreateStoreRequest request);
    String update(UpdateStoreRequest request);
    String delete(List<String> Ids);
}
