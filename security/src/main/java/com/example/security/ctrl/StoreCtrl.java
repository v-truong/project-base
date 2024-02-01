package com.example.security.ctrl;

import com.example.security.dto.store.CreateStoreRequest;
import com.example.security.dto.store.FilterStoreRequest;
import com.example.security.dto.store.UpdateStoreRequest;
import com.example.security.entity.Store;
import com.example.security.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/store")
public class StoreCtrl {
    @Autowired private StoreService storeService;
    @PostMapping("/getAllOwnerId")
    private List<Store> getAllOwnerId(){
        return storeService.getAllOwnerId();
    }
    @PostMapping("/filter")
    public List<Store> filter(@RequestBody FilterStoreRequest request){

        return storeService.filter(request);

    }
    @PostMapping("/getAllByStaffId")
    public List<Store> getAllByStaffId(){
        return storeService.getAllByStaffId();
    }
    @PostMapping("/create")
    public String create(@RequestBody CreateStoreRequest request){
        return storeService.create(request);
    }
    @PostMapping("/update")
    public String update(@RequestBody UpdateStoreRequest request){
        return storeService.update(request);
    }
}
