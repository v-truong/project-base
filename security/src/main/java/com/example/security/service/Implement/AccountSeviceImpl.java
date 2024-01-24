package com.example.security.service.Implement;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.common.config.enums.SortOrderEnum;
import com.example.common.model.ThreadContext;
import com.example.common.response.PageResponse;
import com.example.common.util.SearchUtil;
import com.example.security.dto.account.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.apache.commons.beanutils.PropertyUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security.config.UserInfoUserDetailsService;
import com.example.security.entity.Account;
import com.example.security.repo.AccountRepo;
import com.example.security.service.AccountSevice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
public class AccountSeviceImpl implements AccountSevice {
    @Autowired private UserInfoUserDetailsService userInfoUserDetailsService;
    @Autowired private AccountRepo accountRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public String showTest() {
        
    return "ưaesdkhwkldhjiowe";

    }
    @Override
    public String createAccount(Createaccount createaccount) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, NotFoundException {
        Optional<Account> accountMail = accountRepo.findByEmail(createaccount.getEmail());
        if(!accountMail.isPresent()){
            throw new  NotFoundException();
        }
        Account accountget=accountMail.get();
        if (!accountget.getToken().equals(createaccount.getToken())){
            throw new DuplicateKeyException("invalid token");
        }
        PropertyUtils.copyProperties(accountget, createaccount);
        accountget.setPassword(passwordEncoder.encode(accountget.getPassword()));
        accountget.setEnabled(true);
        accountRepo.save(accountget);
        return "Success Please log in again";
    }
    @Override
    public void saveUserVerificationToken(Account account, String token) {
    }
    @Override
    public String verifyEmail(String token) throws NotFoundException {
        Optional<Account> account = accountRepo.findByEmail(token);
        if (account==null) {
          return "saiiiiiiii";
        }
//        account.setEnabled(true);
//        accountRepo.save(account);
        return"goot";
    }
    @Override
    public String Postavatar(String id,String avartar) throws NotFoundException {
         Account account =accountRepo.findById(id).get();
         if (account==null) {
            throw new NotFoundException();
         }
         account.setAvatar(avartar);
         accountRepo.save(account);
        return "upload avatar success";
    }

    @Override
    public String forgotPassword(ForgotPasswordRequest request) throws NotFoundException {
        Account account=accountRepo.findByUsername(request.getUsername()).get();
        if (account==null) {
            throw new NotFoundException();
        }
        if (!account.getEmail().equals(request.getUsername())) {
            throw new NotFoundException();
        }
        return "Password has been sent to email";



        }

    @Override
    public String updateAccount(UpdateAccountRequest request) throws NotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Optional<Account> account=accountRepo.findById(ThreadContext.getCustomUserDetails().getId());
        if (!account.isPresent()){
            throw new NotFoundException();
        }
        Account accountget=account.get();
        PropertyUtils.copyProperties(accountget,request);
//        accountget.setModifiedDate(LocalDateTime.now());
        accountget.setModifiedUser(ThreadContext.getCustomUserDetails().getUsername());
        accountRepo.save(accountget);
        return "Success";
    }


    @Override
    public Page<Account> advanceSearch(String filter, SearchAccountRequetst searchAccountRequetst, Pageable pageable) {
        if (searchAccountRequetst != null) {
            List<Specification<Account>> specList = getAdvanceSearchSpecList(searchAccountRequetst);
            if (filter != null && !filter.isEmpty()) {
                specList.add(SearchUtil.like("fullTextSearch", "%" + filter + "%"));
            }
            if (specList.size() > 0) {
                Specification<Account> spec = specList.get(0);
                for (int i = 1; i < specList.size(); i++) {
                    spec = spec.and(specList.get(i));

                }
                return accountRepo.findAll(spec,pageable);
            }
        }
        return accountRepo.findAll(pageable);
    }
    private List<Specification<Account>> getAdvanceSearchSpecList(SearchAccountRequetst s){
        List<Specification<Account>> speclít=new ArrayList<>();
        if(s.getName()!=null && !s.getName().isEmpty()){
            speclít.add(SearchUtil.like("name","%"+s.getName()+"%"));
        }
        if (s.getRoles()!=null && !s.getRoles().isEmpty()){
            speclít.add(SearchUtil.like("roles","%"+s.getRoles()+"%"));
        }
        if (s.getEmail()!=null && !s.getEmail().isEmpty()){
            speclít.add(SearchUtil.like("email","%"+s.getEmail()+"%"));
        }
        return  speclít;
    }



}
 