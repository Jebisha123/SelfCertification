package com.backend.service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.access.method.P;

import java.util.List;

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

    @Pattern(regexp="^[A-Za-z]*$",message = "Invalid FirstName.FirstName Should contain only alphabets")
    @Size(max = 20,min = 1,message = "Invalid FirstName.size must be between 1 and 20")
    @Column(nullable = false,length = 40)
    private String firstName;
    @Size(max = 20,min = 1,message = "Invalid LastName.size must be between 1 and 20")
    @Pattern(regexp="^[A-Za-z]*$",message = "Invalid LastName.LastName should contain only alphabets")
    @Column(nullable = false,length = 20)
    private String lastName;
    @Column(nullable = false,length = 50)
    @Pattern(regexp = "^(?=(?:[^.]*.){1,2}[^.]*$)[a-zA-Z0-9._%+-]+[a-zA-Z0-9]@(gmail|yahoo|hotmail|rediffmail|ymail)\\.com$",message = "Invalid email")
    private String email;
    @Size(max = 16,min = 8,message = "Invalid username.Size must be between 8 and 16")
    @Column(nullable = false,length = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]+$",message = "Invalid username.Should be alphanumeric")
    private String username;
//    @Size(min = 16)
    @Column(nullable = false,length = 100)
    @Pattern(regexp = "^[^\\s]+$" ,message = "Invalid Password with blank space")
    private String password;
//    @Column(nullable = false)
    private String role;
    private String question;
    private String answer;
    @ManyToMany
    @JoinTable(
            name = "user_certificates",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "keystore_id")
    )
    private List<KeyStoreEntity> userCertificates;
}
