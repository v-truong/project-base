package com.example.security.config;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import com.example.common.model.ThreadContext;
import com.example.common.model.User;
import com.example.security.entity.Account;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private static final long serialVersionUID = -7615247446131926010L;
    private String id;
    private String username;
 
  private String password;

  private boolean active;

  private boolean locked;
  private List<GrantedAuthority> authorities;
     public CustomUserDetails(Account account) {
        username=account.getUsername();
        password=account.getPassword();
        authorities= Arrays.stream(account.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        User user=new User(account.getId(),account.getUsername(),account.getName(),account.getRoles());
       ThreadContext.setCurrentuser(user);

     }
     @Override
     public Collection<? extends GrantedAuthority> getAuthorities() {
         return authorities;
     }
 
     @Override
     public boolean isAccountNonExpired() {
         return true;
     }
 
     @Override
     public boolean isAccountNonLocked() {
         return true;
     }
 
     @Override
     public boolean isCredentialsNonExpired() {
         return true;
     }
 
     @Override
     public boolean isEnabled() {
         return true;
     }
  

}