package com.example.security.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="config")
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    @Column(name = "type")
    private  String typel;
    @Column(name = "name")
    private  String name;
    @Column(name = "description")
    private  String description;
    @Column(name = "value")
    private  String value;




}
