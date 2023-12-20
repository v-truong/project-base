package com.example.security.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.common.config.Constants;
import com.example.common.entity.EntityBase;
import com.example.common.model.ThreadContext;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

  @Column(name = "roles")
  private String roles;

  @Column(name = "isenabled")
  private boolean isEnabled = false;
  @Column(name = "token")
  private String token;
  @Column(name = "avartar")
  private String avatar;
}
