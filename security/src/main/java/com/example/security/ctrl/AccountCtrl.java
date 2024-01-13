package com.example.security.ctrl;

import com.example.common.model.ThreadContext;
import com.example.security.dto.AuthRequest;
import com.example.security.dto.account.*;
import com.example.security.entity.Account;
import com.example.security.event.ForgotEven;
import com.example.security.event.SendcodeEmailEven;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;





@RestController
@CrossOrigin(origins = "http://localhost:3000")
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
   @PostMapping("/sendcodeEmail")
   public String sendCodeEmail(@RequestBody SendCodeRequest request){
       Optional<Account> account =accountRepo.findByEmail(request.getEmail());
      if(account.isPresent()||account.get().isEnabled()==false){
        Account accountget=account.get();
        LocalDateTime now = LocalDateTime.now();
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
          LocalDateTime inputTime = LocalDateTime.parse(accountget.getModifiedDate(), formatter);
          long seconds = ChronoUnit.SECONDS.between(inputTime, now);
          long timeSenTo = 90;
          if(seconds<=timeSenTo){
              Long timeremaining=timeSenTo-seconds;
              return "There is still time to resend the email:  "+timeremaining;
          }
          publisher.publishEvent(new SendcodeEmailEven(accountget));
          return "Success";
      }
       if(account.isPresent()||account.get().isEnabled()==true){
           throw new DuplicateKeyException("email has been registered");
       }

       Account accountsend=new Account();
       accountsend.setEmail(request.getEmail());
       accountsend.setCreatedUser(request.getEmail());
       accountRepo.save(accountsend);
       publisher.publishEvent(new SendcodeEmailEven(accountsend));

       return "Success";
    }
    @PostMapping("/sendcodeEmailfogot")
    public String sendCodeEmailForgot(@RequestBody FogotSendmaillRequest request) throws NotFoundException {
        Optional<Account> account =accountRepo.findByUsername(request.getUsername());
        if(!account.isPresent()){
            throw new NotFoundException();
        }
        Account accountsend=account.get();
        publisher.publishEvent(new ForgotEven(accountsend));
        return accountsend.getEmail();
    }
    @PostMapping("/fogot")
    public String fogot(@RequestBody ForgotPasswordRequest request) throws NotFoundException {
        Optional<Account> account =accountRepo.findByUsername(request.getUsername());
        if(!account.isPresent()){
            throw new NotFoundException();
        }
        Account accountsend=account.get();
        if (!request.getCode().equals(accountsend.getCode())){
            throw new DuplicateKeyException("code fall");
        }
       return "Success";
    }


   @PostMapping("/login")
   @ResponseStatus(HttpStatus.OK)
   public GetAccountDto authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws NotFoundException {
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
           GetAccountDto getAccountDto=new GetAccountDto();
           getAccountDto.setName(accountget.getName());
           getAccountDto.setToken(jwtService.generateToken(authRequest.getUsername()));
           getAccountDto.setAvatar(accountget.getAvatar());
           getAccountDto.setUsername(accountget.getUsername());
           getAccountDto.setPhone(accountget.getPhone());
           getAccountDto.setId(accountget.getId());

           return getAccountDto;
       } else {
           throw new NotFoundException();
       }
     
   }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public String register(@RequestBody Createaccount createaccount, final HttpServletRequest request) throws DuplicateKeyException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, NotFoundException {
           accountSevice.createAccount(createaccount);
           Account account = new Account();

           PropertyUtils.copyProperties(account, createaccount);
           return "Successful registration, please verify email";
    }
    @PostMapping("forgot")
    @ResponseStatus(HttpStatus.OK)
    public String forget(@RequestBody ForgotPasswordRequest request) throws NotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Optional<Account> account=accountRepo.findByUsername(request.getUsername());
        if (!account.isPresent()) {
            throw new NotFoundException();
        }
        Account accountget = account.get();
        if (!accountget.getCode().equals(request.getCode())) {
            throw new NotFoundException();
        }
        PropertyUtils.copyProperties(accountget,request);
        accountRepo.save(accountget);

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
    @PostMapping("/updateaccount")
    @ResponseStatus(HttpStatus.OK)
    public String updateaccount(@RequestBody UpdateAccountRequest request) throws NotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
       return accountSevice.updateAccount(request);
    }
    @GetMapping("/checktoken")
    @ResponseStatus(HttpStatus.OK)
    public String checktoken() throws NotFoundException {
       if(ThreadContext.getCustomUserDetails().getUsername()==null||ThreadContext.getCustomUserDetails().getUsername().isEmpty()){
           throw new NotFoundException();
       }
       return "Success";
    }
    @PostMapping("/checkEmailRegister")
    public String checkEmailRigister(@RequestBody CheckCodeRigisterRequest request) throws NotFoundException {
       Optional<Account> account=accountRepo.findByEmail(request.getEmail());
       if (!account.isPresent()){
           throw new NotFoundException();

       }
       Account accountget=account.get();
       if(!accountget.getCode().equals(request.getCode())){
           throw new DuplicateKeyException("code fail");
       }
       return "Success";

    }







    
    
}