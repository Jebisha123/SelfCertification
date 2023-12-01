package com.backend.service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // maps this User object with User table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "user",uniqueConstraints = {@UniqueConstraint(columnNames = {"username"},name = "unique1"),@UniqueConstraint(columnNames = {"email"},name = "unique2")})

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(max = 20)
    @Pattern(regexp="^[A-Za-z]*$",message = "Invalid Input. Should contain only alphabets")
    @Column(nullable = false,length = 40)
    private String firstName;
    @Size(max = 20)
    @Pattern(regexp="^[A-Za-z]*$",message = "Invalid Input. Should contain only alphabets")
    @Column(nullable = false,length = 40)
    private String lastName;
    @Column(nullable = false,length = 100)
    @Pattern(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}")
    private String email;
    @Size(max = 16,min = 8)
    @Column(nullable = false,length = 100)
    private String username;
//    @Size(max = 16,min = 8)
    @Column(nullable = false,length = 100)
    private String password;
//    @Column(nullable = false)
    private String role;
}
