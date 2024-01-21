package com.example.security.entity;

import com.example.common.entity.EntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
  private String phone;

  @Column(name = "roles")
  private String roles;

  @Column(name = "isenabled")
  private boolean isEnabled = false;
  @Column(name = "code")
  private Integer code;
  @Column(name = "avartar")
  private String avatar;
  @Column(name = "time_live_code")
  private String timeLiveCode;
  private String token;
  @PrePersist
  protected void onCreate() {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    String formattedNow = now.format(formatter);
    setCreatedDate(formattedNow);
    setModifiedDate(formattedNow);
  }
  @PreUpdate
  protected void onUpdate() {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    String formattedNow = now.format(formatter);
    setModifiedDate(formattedNow);
  }
}
