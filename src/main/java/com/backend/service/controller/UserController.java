package com.backend.service.controller;

import com.backend.service.model.LoginCredentials;
import com.backend.service.model.User;
import com.backend.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    UserRepository userRepository;
    PasswordEncoder encoder = new BCryptPasswordEncoder(16);

    @PostMapping("/login")
    public ResponseEntity<String> validateLogin(@RequestBody LoginCredentials loginCredentials) {
        User user = userRepository.findUserByUsername(loginCredentials.getUsername());
        if(encoder.matches(loginCredentials.getPassword(), user.getPassword())) {
            return ResponseEntity.ok().body("Login Successful");
        } else {
            return ResponseEntity.status(401).body("Login Failed");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody @Validated User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        User createdUser = userRepository.save(user);
        return ResponseEntity.created(URI.create("")).body(createdUser);
    }
}
