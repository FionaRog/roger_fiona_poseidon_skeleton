package com.nnk.springboot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing an application user stored in the users table.
 */
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    // @Validation utile? plutôt à mettre dans les DTO - utiliser plutot @columnn(nullable=false)
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name= "Id")
    private Integer id;
    private String username;
    private String password;
    private String fullname;
    private String role;

}
