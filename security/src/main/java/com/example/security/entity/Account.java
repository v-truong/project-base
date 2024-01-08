package com.example.security.entity;

import com.example.common.entity.EntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

  @Column(name = "username",unique = true)
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
  @Column(name = "code")
  private Integer code;
  @Column(name = "avartar")
  private String avatar;
}
