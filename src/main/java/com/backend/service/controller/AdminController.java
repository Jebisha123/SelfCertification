package com.backend.service.controller;

import com.backend.service.model.KeyStoreEntity;
import com.backend.service.model.User;
import com.backend.service.repository.KeyStoreRepository;
import com.backend.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")

public class AdminController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    KeyStoreRepository keyStoreRepository;
    @PostMapping("/associate")
    public ResponseEntity<String> associateCertificate(@RequestParam Long userId, @RequestParam Long keyStoreId)
    {

        User user = userRepository.findById(userId).get();
        KeyStoreEntity keyStoreEntity = keyStoreRepository.findById(keyStoreId).get();
        List<KeyStoreEntity> userCertificates = user.getUserCertificates();
        userCertificates.add(keyStoreEntity);
        userRepository.save(user);
        return ResponseEntity.ok().body("Associated Successfully");
    }
}
