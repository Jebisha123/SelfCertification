package com.backend.service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // maps this User object with User table
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user",uniqueConstraints = {@UniqueConstraint(columnNames = {"username"},name = "unique1"),@UniqueConstraint(columnNames = {"email"},name = "unique2")})

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false,length = 100)
    private String email;
    @Column(nullable = false,length = 100)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String role;
}
