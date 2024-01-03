package com.example.security.entity;

import com.example.common.entity.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="ticket")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket extends EntityBase {
    private String name;
}
