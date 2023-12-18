package com.example.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.security.entity.Account;
import com.example.security.repo.AccountRepo;


import java.util.Optional;
@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepo repository;
    
    // private final  ConfirmationTokenService confirmationTokenService;
   
    // private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> userInfo = repository.findByUsername(username);
        return userInfo.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

    }
    // public String signUpUser(Account account) {

    //     boolean userExists = repository.findByEmail(account.getEmail()).isPresent();

    //     if (userExists) {

    //         // TODO: if email is not confirmed send confirmation email again.

    //         throw new IllegalStateException("email is already taken.");
    //     }

    //     String encodedPassword = bCryptPasswordEncoder.encode(account.getPassword());

    //     account.setPassword(encodedPassword);

    //     repository.save(account);

    //     String token = UUID.randomUUID().toString();

    //     ConfirmationToken confirmationToken = new ConfirmationToken(
    //     );

    //     confirmationTokenService.saveConfirmationToken(confirmationToken);

    //     return token;
    // }
    // public void enableUser(String email) {

    //     userRepository.enableUser(email);
    // }
}
