package com.example.security.service.Implement;

import com.example.common.config.Constants;
import com.example.common.model.ThreadContext;
import com.example.security.dto.store.CreateStoreRequest;
import com.example.security.dto.store.FilterStoreRequest;
import com.example.security.dto.store.UpdateStoreRequest;
import com.example.security.entity.Account;
import com.example.security.entity.Store;
import com.example.security.entity.Technical;
import com.example.security.repo.AccountRepo;
import com.example.security.repo.StoreRepo;
import com.example.security.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StoreImpl implements StoreService {
    @Autowired
    private StoreRepo storeRepo;
    @Autowired
    private AccountRepo accountRepo;


    @Override
    public List<Store> filter(FilterStoreRequest request) {
        if(request.getType().equals(Constants.TYPE_STORE_ALL)){
            List<Store> storeList=storeRepo.findByOwnerIdOrStaffIdsAndIsDelete(ThreadContext.getCustomUserDetails().getId(),
                    ThreadContext.getCustomUserDetails().getId(),Constants.ISDELETE_TRUE);
            return storeList;
        }
        if (request.getType().equals(Constants.TYPE_STORE_JOINED)){
            List<Store> stores=storeRepo.findByStaffIdsLikeAndIsDelete(ThreadContext.getCustomUserDetails().getId(),Constants.ISDELETE_TRUE);
            return  stores;
        }
        if (request.getType().equals(Constants.TYPE_STORE_CREATED)){
            List<Store> stores=storeRepo.findAllByOwnerIdAndIsDelete(ThreadContext.getCustomUserDetails().getId(),Constants.ISDELETE_TRUE);
            return  stores;
        }
        if (request.getType().equals(Constants.TYPE_STORE_DELETED)) {
        List<Store> stores =storeRepo.findByOwnerIdOrStaffIdsAndIsDelete(ThreadContext.getCustomUserDetails().getId(),
                ThreadContext.getCustomUserDetails().getId(),Constants.ISDELETE_FALSE);
        return stores;
        }

        return null;
    }

    @Override
    public List<Store> getAllOwnerId() {
        List<Store> stores=storeRepo.findAllByOwnerIdAndIsDelete(ThreadContext.getCustomUserDetails().getId(),Constants.ISDELETE_TRUE);
        return stores;
    }

    @Override
    public List<Store> getAllByStaffId() {
        List<Store> stores=storeRepo.findByStaffIdsLikeAndIsDelete(ThreadContext.getCustomUserDetails().getId(),Constants.ISDELETE_TRUE);
        return stores;
    }
    @Override
    public String create(CreateStoreRequest request) {
        if (!ThreadContext.getCustomUserDetails().getRole().equals(Constants.ROLE_SALESPERSON)) {
            throw new AccessDeniedException("");
        }
        List<Account> accounts = accountRepo.findByIdIn(request.getStaffIds());
        Map<String, Account> accountMaps = accounts.stream().collect(Collectors.toMap(Account::getId, Function.identity()));
        for (String account : request.getStaffIds()) {
            Account accountFor = accountMaps.get(account);
            if (accountFor == null) {
                throw new DuplicateKeyException("id:"+account+ ":does not exist");
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String element : request.getStaffIds()) {
            stringBuilder.append(element).append(",");
        }
        String listAsString = stringBuilder.toString();
        if (listAsString.length() > 0) {
            listAsString = listAsString.substring(0, listAsString.length() - 2);
        }
        Store store = new Store();
        store.setOwnerId(ThreadContext.getCustomUserDetails().getId());
        store.setName(request.getName());
        store.setStaffIds(listAsString);
        storeRepo.save(store);
        return "Success";
    }

    @Override
    public String update(UpdateStoreRequest request) {
        Optional<Store> storeOptional = storeRepo.findById(request.getId());
        if (!storeOptional.isPresent()) {
            throw new DuplicateKeyException("Id not fount");
        }
        Store storeget = storeOptional.get();
        if (!ThreadContext.getCustomUserDetails().getId().equals(storeget.getOwnerId())) {
            throw new AccessDeniedException("not have access");
        }
        List<Account> accounts = accountRepo.findByIdIn(request.getStaffIds());
        Map<String, Account> accountMaps = accounts.stream().collect(Collectors.toMap(Account::getId, Function.identity()));
        for (String account : request.getStaffIds()) {
            Account accountFor = accountMaps.get(account);
            if (accountFor == null) {
                throw new DuplicateKeyException(accountFor.getId() + "does not exist");
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String element : request.getStaffIds()) {
            stringBuilder.append(element).append(",");
        }
        String listAsString = stringBuilder.toString();
        if (listAsString.length() > 0) {
            listAsString = listAsString.substring(0, listAsString.length() - 2);
        }
        storeget.setName(request.getName());
        storeget.setStaffIds(listAsString);
        storeRepo.save(storeget);
        return "Success";
    }

    @Override
    public String delete(List<String> ids) {
        List<Store> lstStore=new ArrayList<>();
        List<Store> storeList =storeRepo.findAllById(ids);
        Map<String,Store> storeMap=storeList.stream().collect(Collectors.toMap(Store::getId,Function.identity()));
        for (String id: ids) {
            Store storeFor =storeMap.get(id);
            if (storeFor==null){
                throw new DuplicateKeyException(id+"not fount");
            }
            if(!ThreadContext.getCustomUserDetails().getId().equals(storeFor.getOwnerId())) {
                throw new AccessDeniedException("accessDenied");
            }
            storeFor.setIsDelete(Constants.ISDELETE_FALSE);
            lstStore.add(storeFor);
        }
        storeRepo.saveAll(lstStore);


        return null;
    }
}
