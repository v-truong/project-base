package com.example.security.ctrl;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.example.common.model.ThreadContext;
import com.example.security.dto.AuthRequest;
import com.example.security.dto.Createaccount;

import com.example.security.service.AccountSevice;
import com.example.security.service.JwtService;



@RestController
@RequestMapping("/api/v1/users")
public class AccountCtrl {

    @Autowired  
    private JwtService jwtService;
    @Autowired private AccountSevice accountSevice;
    
    @Autowired
    private AuthenticationManager authenticationManager;

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
       Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
       if (authentication.isAuthenticated()) {
           
           return jwtService.generateToken(authRequest.getUsername());
       } else {
           throw new UsernameNotFoundException("invalid user request !");
       }
     
   }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public Createaccount register(@RequestBody Createaccount createaccount) throws DuplicateKeyException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
           accountSevice.createAccount(createaccount);
           return createaccount;
    }
   @PostMapping("/registers")
    @ResponseStatus(HttpStatus.OK)
    public String registers(){
           return "ok";
    }

}
