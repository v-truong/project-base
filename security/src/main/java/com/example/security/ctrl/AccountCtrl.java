package com.example.security.ctrl;

import com.example.common.model.ThreadContext;
import com.example.security.dto.AuthRequest;
import com.example.security.dto.account.Createaccount;
import com.example.security.entity.Account;
import com.example.security.event.RegistrationCompleteEvent;
import com.example.security.repo.AccountRepo;
import com.example.security.service.AccountSevice;
import com.example.security.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;





@RestController
@RequestMapping("/api/v1/users")
public class AccountCtrl {
      private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

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
   public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws NotFoundException {
       Optional<Account> account =accountRepo.findByUsername(authRequest.getUsername());
       if(!account.isPresent()){
           throw new NotFoundException();
       }
       Account accountget = account.get();
       if (accountget.isEnabled()==false) {
           throw new NotFoundException();
          
       }
       Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
       if (authentication.isAuthenticated()) {
           
           return jwtService.generateToken(authRequest.getUsername());
       } else {
           throw new NotFoundException();
       }
     
   }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public String register(@RequestBody Createaccount createaccount, final HttpServletRequest request) throws DuplicateKeyException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
           accountSevice.createAccount(createaccount);
           Account account = new Account();

           PropertyUtils.copyProperties(account, createaccount);
              publisher.publishEvent(new RegistrationCompleteEvent(account, applicationUrl(request)));
           return "Successful registration, please verify email";
    }
    @PostMapping("forgot")
    @ResponseStatus(HttpStatus.OK)
    public String forget(){

        return "ok";
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
    @PostMapping("replace-avatar")
    public String PostAvartar(@RequestParam("id") String id,@RequestParam("avatar") MultipartFile avatar) throws IOException, NotFoundException {
        Path staticPath = Paths.get("static");
        Path imagePath = Paths.get("images");
        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }
        Path file = CURRENT_FOLDER.resolve(staticPath)
                .resolve(imagePath).resolve(avatar.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(file)) {
            os.write(avatar.getBytes());
        }
        // accountSevice.Postavatar(id, imagePath.resolve(avatar.getOriginalFilename()).toString());
        return accountSevice.Postavatar(id, imagePath.resolve(avatar.getOriginalFilename()).toString());
    }
    @GetMapping("/testserver")
    @ResponseStatus(HttpStatus.OK)
    public String getMethodName() throws NotFoundException {
        throw new NotFoundException() ;
    }
    @GetMapping("/testserver1")
    @ResponseStatus(HttpStatus.OK)
    public String getMethodName1() {
        System.out.println("okokoko=========================");
       return "chay dc" ;
    }

    
    
}