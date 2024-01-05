package com.example.security.entity;

import com.example.common.entity.EntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Account extends EntityBase{

  @Column(name = "name")
  private String name;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

   @Column(name = "email")
  private String email;
  @Column(name = "phone")
  private String Phone;

  @Column(name = "roles")
  private String roles;

  @Column(name = "isenabled")
  private boolean isEnabled = false;
  @Column(name = "tokenemail")
  private String tokenEmail;
  @Column(name = "avartar")
  private String avatar;
}
