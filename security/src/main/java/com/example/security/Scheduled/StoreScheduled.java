package com.example.security.Scheduled;

import com.example.common.config.Constants;
import com.example.security.entity.Store;
import com.example.security.repo.StoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class StoreScheduled {
    @Autowired private StoreRepo storeRepo;
    @Scheduled(fixedRate = 1000) // Thực hiện công việc mỗi 5 giây
    public void updateStatusExpire() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedNow = now.format(formatter);
        List<Store> storeList=storeRepo.findByExpireDateThanOrEqual(formattedNow);
        for (Store store:storeList) {
            store.setStatus(Constants.STATUS_STORE_DEACTIVE);
        }
    }
}
