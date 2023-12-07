package com.backend.service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.access.method.P;

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
    @Size(max = 20,min = 1)
    @Pattern(regexp="^[A-Za-z]*$",message = "Invalid FirstName.FirstName Should contain only alphabets")
    @Column(nullable = false,length = 40)
    private String firstName;
    @Size(max = 20,min = 1)
    @Pattern(regexp="^[A-Za-z]*$",message = "Invalid LastName.LastName should contain only alphabets")
    @Column(nullable = false,length = 40)
    private String lastName;
    @Column(nullable = false,length = 100)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[com]{3,}$",message = "Invalid email")
    private String email;
    @Size(max = 16,min = 8)
    @Column(nullable = false,length = 100)
    @Pattern(regexp = "^[a-zA-Z0-9]+$",message = "Invalid username.")
    private String username;
//    @Size(min = 16)
    @Column(nullable = false,length = 100)
    @Pattern(regexp = "^[^\\s]+$" ,message = "Invalid Password")
    private String password;
//    @Column(nullable = false)
    private String role;
}
