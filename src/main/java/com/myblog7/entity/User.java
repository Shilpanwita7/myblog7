package com.myblog7.entity;


//for sign up using spring security

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String username;
    private String email;
    private  String password;

//    EAGER load all the table in cache memory, LAZY will load only those tables which are required
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", //this is the 3rd table automatically gets created
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"), // parent table, user created then the roles allocated to the user
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles; //manyTomany mapping so set has taken here
}
