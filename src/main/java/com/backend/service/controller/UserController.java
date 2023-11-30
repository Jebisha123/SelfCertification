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
import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class UserController {
    @Autowired
    UserRepository userRepository;
    PasswordEncoder encoder = new BCryptPasswordEncoder(16);
    @PostMapping("/login")
    public ResponseEntity<String> validateLogin(@RequestBody LoginCredentials loginCredentials) {
try {
    User user = userRepository.findUserByUsername(loginCredentials.getUsername());
    if (encoder.matches(loginCredentials.getPassword(), user.getPassword())) {
        return ResponseEntity.ok().body("Login Successful");
    } else {
        return ResponseEntity.status(401).body("Login Failed");
    }
}
catch(Exception e)
{}
        return ResponseEntity.status(401).body("Login Failed");
    }

//    @PostMapping("/register")
//    public ResponseEntity<User> createUser(@RequestBody @Validated User user) {
//        try {
//            user.setPassword(encoder.encode(user.getPassword()));
//            User createdUser = userRepository.save(user);
//            return ResponseEntity.created(URI.create("")).body(createdUser);
//
//    } catch (Exception e) {
//            System.out.println(e);
//        }
//      return ResponseEntity.status(500).build() ;
//
//
//
//    }
@PostMapping("/register")
public ResponseEntity<String> createUser(@RequestBody @Validated User user) {
    try {
        user.setPassword(encoder.encode(user.getPassword()));
        User createdUser = userRepository.save(user);
        return ResponseEntity.status(201).body("User Registered");

    } catch (Exception e) {
        System.out.println(e);
    }
    return ResponseEntity.status(500).body("User Already Exists");

}

    @GetMapping("/getalluser")
    public  ResponseEntity<List<User>> getAllUser()
    {
        return ResponseEntity.ok().body(userRepository.findAll());
    }
    @GetMapping("/getbyusername")
    public  ResponseEntity<String> getbyusername(@RequestParam(value="username") String username)
    {
        User user = userRepository.findUserByUsername(username);
        try {
            if (user.getUsername().isEmpty())
                return ResponseEntity.ok().body("UserName available");
            else
                return ResponseEntity.status(401).body("UserName already exists");
        }
        catch (Exception e)
        {return ResponseEntity.ok().body("UserName available");}

    }
    @GetMapping("/getbyemail")
    public  ResponseEntity<String> getbyemail(@RequestParam(value="email") String email)
    {
        User user = userRepository.findUserByEmail(email);
        try {
            if (user.getEmail().isEmpty())
                return ResponseEntity.ok().body("Email available");
            else
                return ResponseEntity.status(401).body("Email already exists");
        }
        catch (Exception e)
        {return ResponseEntity.ok().body("Email available");}

    }
}
