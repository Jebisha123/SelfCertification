package com.backend.service.controller;

import com.backend.service.model.LoginCredentials;
import com.backend.service.model.User;
import com.backend.service.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")

public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity<Object> validateLogin(@RequestBody LoginCredentials loginCredentials) {
        HashMap<String,String> result = new HashMap<>();
        try {
            User user = userRepository.findUserByUsername(loginCredentials.getUsername());


            if (user != null && user.getUsername().equals(loginCredentials.getUsername()) && encoder.matches(loginCredentials.getPassword(), user.getPassword())) {
                result.put("result", "Login Successful");
                return ResponseEntity.ok().body(result);
            } else {
                result.put("result", "Login Failed");
                return ResponseEntity.status(401).body(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("result", "Login Failed");
            return ResponseEntity.status(401).body(result);
        }
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
    public ResponseEntity<Object> createUser(@RequestBody @Validated User user) {
        Map<String, Object> createdResponse = new LinkedHashMap<>();
        System.out.println(user);
        if (user.getPassword().length() > 16 || user.getPassword().length() < 8)
            return ResponseEntity.status(400).body("Password length should be 8-16");
        try {
            user.setPassword(encoder.encode(user.getPassword()));
            User createdUser = userRepository.save(user);
            HashMap<String,String> showUser = new HashMap<>();
            showUser.put("UserName",user.getUsername());
            showUser.put("Email",user.getEmail());
            createdResponse.put("User Registered", showUser);
            return ResponseEntity.status(201).body(createdResponse);

        } catch (Exception e) {
           System.out.println(e);

        }
        createdResponse.put("User Already Exists", null);
        return ResponseEntity.status(400).body(createdResponse);

    }

    @GetMapping("/getalluser")
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok().body(userRepository.findAll());
    }

    @GetMapping("/getbyusername")
    public ResponseEntity<String> getbyusername(@RequestParam(value = "username") String username) {
        User user = userRepository.findUserByUsername(username);
        try {
            if (user.getUsername().isEmpty())
                return ResponseEntity.ok().body("UserName available");
            else
                return ResponseEntity.status(401).body("UserName already exists");
        } catch (Exception e) {
            return ResponseEntity.ok().body("UserName available");
        }

    }

    @GetMapping("/getbyemail")
    public ResponseEntity<String> getbyemail(@RequestParam(value = "email") String email) {
        User user = userRepository.findUserByEmail(email);
        try {
            if (user.getEmail().isEmpty())
                return ResponseEntity.ok().body("Email available");
            else
                return ResponseEntity.status(401).body("Email already exists");
        } catch (Exception e) {
            return ResponseEntity.ok().body("Email available");
        }

    }
}
