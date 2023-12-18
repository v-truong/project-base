package com.example.security.ctrl;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.example.common.model.ThreadContext;
import com.example.security.dto.AuthRequest;
import com.example.security.dto.account.Createaccount;
import com.example.security.entity.Account;
import com.example.security.event.RegistrationCompleteEvent;
import com.example.security.repo.AccountRepo;
import com.example.security.service.AccountSevice;
import com.example.security.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;



@RestController
@RequestMapping("/api/v1/users")
public class AccountCtrl {

    @Autowired  
    private JwtService jwtService;
    @Autowired private AccountSevice accountSevice;
    @Autowired private  ApplicationEventPublisher publisher;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired private AccountRepo accountRepo;

   @GetMapping()
   public String a(){
    if(ThreadContext.getCustomUserDetails().getRole() == "admin"){
        return "dung user";
    }
    System.out.println(ThreadContext.getCustomUserDetails().getRole());
    return "sai user";

   }
   @PostMapping("/login")
   @ResponseStatus(HttpStatus.OK)
   public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
       Optional<Account> account =accountRepo.findByUsername(authRequest.getUsername());
       if(!account.isPresent()){
         throw new UsernameNotFoundException("account does not exist"); 
       }
       Account accountget = account.get();
       if (accountget.isEnabled()==false) {
        throw new UsernameNotFoundException("If you do not activate your account, your account will be deleted one minute after registration"); 
          
       }
       Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
       if (authentication.isAuthenticated()) {
           
           return jwtService.generateToken(authRequest.getUsername());
       } else {
           throw new UsernameNotFoundException("invalid user request !");
       }
     
   }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public String register(@RequestBody Createaccount createaccount, final HttpServletRequest request) throws DuplicateKeyException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
           accountSevice.createAccount(createaccount);
           Account account = new Account();
           PropertyUtils.copyProperties(account, createaccount);
              publisher.publishEvent(new RegistrationCompleteEvent(account, applicationUrl(request)));
           return "okkkkkk";
    }
   @PostMapping("/registers")
    @ResponseStatus(HttpStatus.OK)
    public String registers(){
           return "ok";
    }
    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token) throws NotFoundException{
        
      return accountSevice.verifyEmail(token);
    }



    public String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }

}
