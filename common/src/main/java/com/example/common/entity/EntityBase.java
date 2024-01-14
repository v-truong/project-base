package com.example.common.entity;


import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.example.common.config.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
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
  // @Pattern(regexp = DaoConst.DATE_TIME_REGEX, message = "{date-time.Pattern.message}")

  @Column(name = "created_date", updatable = false, nullable = false)
  private String createdDate;
  @LastModifiedDate
  @Column(name = "modified_date")
  private String modifiedDate;
  
  
//  @CreatedBy
  @Column(name = "created_user", updatable = false, nullable = false)
  private String createdUser;

  @LastModifiedBy
  @Column(name = "modified_user")
  private String modifiedUser;
  @Column(name = "is_delete")
  private Integer isDelete =0;
}
