package com.example.security.ctrl;

import com.example.common.config.Constants;
import com.example.common.config.enums.SortOrderEnum;
import com.example.common.model.ThreadContext;
import com.example.common.response.PageResponse;
import com.example.common.util.SearchUtil;
import com.example.security.dto.AuthRequest;
import com.example.security.dto.account.*;
import com.example.security.entity.Account;
import com.example.security.event.ForgotEven;
import com.example.security.event.SendcodeEmailEven;
import com.example.security.repo.AccountRepo;
import com.example.security.service.AccountSevice;
import com.example.security.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import java.util.UUID;


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
    @Autowired private PasswordEncoder passwordEncoder;



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
      if(account.isPresent()&&account.get().isEnabled()==false){
        Account accountget=account.get();
        LocalDateTime now = LocalDateTime.now();
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
          LocalDateTime inputTime = LocalDateTime.parse(accountget.getTimeLiveCode(), formatter);
          long seconds = ChronoUnit.SECONDS.between(inputTime, now);
          long timeSenTo = 90;
          if(seconds<=Constants.TIME_lIVE_CODE){
              Long timeremaining=timeSenTo-seconds;
              return "There is still time to resend the email:  "+timeremaining;
          }
          publisher.publishEvent(new SendcodeEmailEven(accountget));
          return "Success";
      }
       if(account.isPresent()&&account.get().isEnabled()==true){
           throw new DuplicateKeyException("email has been registered");
       }

       Account accountsend=new Account();
       accountsend.setEmail(request.getEmail());
       accountsend.setCreatedUser(request.getEmail());
       LocalDateTime now = LocalDateTime.now();
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
       String formattedNow = now.format(formatter);
       accountsend.setTimeLiveCode(formattedNow);
       accountRepo.save(accountsend);
       publisher.publishEvent(new SendcodeEmailEven(accountsend));

       return "Success";
    }
    @PostMapping("/sendCodeEmailForgot")
    public String sendCodeEmailForgot(@RequestBody FogotSendmaillRequest request) throws NotFoundException {
        Optional<Account> account =accountRepo.findByUsername(request.getUsername());
        if(!account.isPresent()){
            throw new NotFoundException();
        }

        Account accountsend=account.get();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedNow = now.format(formatter);
        accountsend.setTimeLiveCode(formattedNow);

        accountRepo.save(accountsend);
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
        if (!request.getToken().equals(accountsend.getToken())){
            throw new DuplicateKeyException("token fall");
        }

       return "Success";
    }
    @PostMapping("/checkCodeFogot")
    public String checkCodeFogot(@RequestBody CheckCodeFogotRequest request){
       Optional<Account> accountOptional =accountRepo.findByUsername(request.getUsername());
       if(!accountOptional.isPresent()){
           throw new  DuplicateKeyException(request.getUsername()+"not fount");
       }
        Account accountget=accountOptional.get();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime inputTime = LocalDateTime.parse(accountget.getTimeLiveCode(), formatter);
        long seconds = ChronoUnit.SECONDS.between(inputTime, now);
        System.out.println(seconds +"");
        System.out.println(accountget.getCode());
        if(!accountget.getCode().equals(request.getCode()) || seconds > Constants.TIME_lIVE_CODE){
            throw new DuplicateKeyException("code fail");
        }
        String verificationToken = UUID.randomUUID().toString();
        accountget.setToken(verificationToken);
        accountRepo.save(accountget);
       return verificationToken;
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
    public String forgot(@RequestBody ForgotPasswordRequest request) throws NotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Optional<Account> account=accountRepo.findByUsername(request.getUsername());
        if (!account.isPresent()) {
            throw new NotFoundException();
        }
        Account accountget = account.get();
        if (!accountget.getToken().equals(request.getToken())) {
            throw new NotFoundException();
        }
        PropertyUtils.copyProperties(accountget,request);
        accountget.setPassword(passwordEncoder.encode(accountget.getPassword()));
        accountRepo.save(accountget);

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
        System.out.println("okokoko============xzsxadas=============");
       return "chay dcdsiakjdikajsmkajskmoas" ;
    }
    @PostMapping("/updateaccount")
    @ResponseStatus(HttpStatus.OK)
    public String updateaccount(@RequestBody UpdateAccountRequest request) throws NotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
       return accountSevice.updateAccount(request);
    }
    @GetMapping("/checkToken")
    @ResponseStatus(HttpStatus.OK)
    public GetAccountDto checktoken() throws NotFoundException {

       if(ThreadContext.getCustomUserDetails().getUsername()==null||ThreadContext.getCustomUserDetails().getUsername().isEmpty()){
           throw new NotFoundException();
       }
        System.out.println(ThreadContext.getCustomUserDetails().getUsername());
        Optional<Account> account =accountRepo.findByUsername(ThreadContext.getCustomUserDetails().getUsername());
       if(!account.isPresent()){
            throw new NotFoundException();
        }
        Account accountget = account.get();
        GetAccountDto getAccountDto=new GetAccountDto();
        getAccountDto.setName(accountget.getName());
        getAccountDto.setToken(jwtService.generateToken(ThreadContext.getCustomUserDetails().getUsername()));
        getAccountDto.setAvatar(accountget.getAvatar());
        getAccountDto.setUsername(accountget.getUsername());
        getAccountDto.setPhone(accountget.getPhone());
        getAccountDto.setId(accountget.getId());

       return getAccountDto;
    }
    @PostMapping("/checkEmailRegister")
    public String checkEmailRigister(@RequestBody CheckCodeRigisterRequest request) throws NotFoundException {
       Optional<Account> account=accountRepo.findByEmail(request.getEmail());
       if (!account.isPresent()){
           throw new NotFoundException();

       }

        Account accountget=account.get();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime inputTime = LocalDateTime.parse(accountget.getTimeLiveCode(), formatter);
        long seconds = ChronoUnit.SECONDS.between(inputTime, now);

       if(!accountget.getCode().equals(request.getCode())||seconds>= Constants.TIME_lIVE_CODE){
           throw new DuplicateKeyException("code fail");
       }
       String verificationToken = UUID.randomUUID().toString();
       accountget.setToken(verificationToken);
       accountRepo.save(accountget);
       return verificationToken;

    }
    @PostMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<Account> advanceSearch (@RequestParam(required = false) String filter, @Valid @RequestBody SearchAccountRequetst searchAccountRequetst,
                                          @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer page,
                                          @Positive @RequestParam(required = false) Integer size, @RequestParam(required = false) String sort,
                                          @RequestParam(required = false) SortOrderEnum order){
        Pageable pageable = SearchUtil.getPageableFromParam(page, size, sort, order);
        Page<Account> pageData = accountSevice.advanceSearch(filter, searchAccountRequetst, pageable);
        for(int i=0;i<pageData.getContent().size();i++){
        System.out.println(pageData.getContent().size());
        pageData.getContent().get(i).setPassword("");
            pageData.getContent().get(i).setToken("");
            pageData.getContent().get(i).setPassword("");
        }
//        System.out.println(pageData.getContent());
        return new PageResponse<>(pageData);
    }






    
    
}