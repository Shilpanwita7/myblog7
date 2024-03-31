package com.myblog7.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


//@Data will not work here, we have to provide @Setter & @Getter
@Setter
@Getter
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 60)
    private String name;
}
