package com.example.common.entity;


import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.common.config.Constants;
import com.example.common.util.DaoConst;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class EntityBase {
  @Id
  @NotNull
  @Column(name = "id", length = Constants.ID_MAX_LENGTH)
  @Size(max  = Constants.ID_MAX_LENGTH)
  // @UuidGenerator(style="UUID")
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
  @Past
  @DateTimeFormat(pattern = "yyyyMMddHHmmss")
  @CreatedDate
  @Column(name = "created_date", updatable = false, nullable = false, columnDefinition = "TIMESTAMP")
  private LocalDateTime createdDate;
  @LastModifiedDate
  @Column(name = "modified_date", columnDefinition = "TIMESTAMP")
  private LocalDateTime modifiedDate;
  
  
  @CreatedBy
  @Column(name = "created_user", updatable = false, nullable = false)
  private String createdUser;

  @LastModifiedBy
  @Column(name = "modified_user")
  private String modifiedUser;
}
