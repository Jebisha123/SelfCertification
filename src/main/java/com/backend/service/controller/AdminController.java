package com.backend.service.controller;

import com.backend.service.model.AssociateRequest;
import com.backend.service.model.KeyStoreEntity;
import com.backend.service.model.User;
import com.backend.service.repository.KeyStoreRepository;
import com.backend.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")

public class AdminController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    KeyStoreRepository keyStoreRepository;

    @PostMapping("/associate")
//    public ResponseEntity<String> associateCertificate(@RequestParam Long userId, @RequestParam Long keyStoreId)
//    {
//
//        User user = userRepository.findById(userId).get();
//        KeyStoreEntity keyStoreEntity = keyStoreRepository.findById(keyStoreId).get();
//        List<KeyStoreEntity> userCertificates = user.getUserCertificates();
//        userCertificates.add(keyStoreEntity);
//        userRepository.save(user);
//        return ResponseEntity.ok().body("Associated Successfully");
//    }
    public ResponseEntity<Object> associateCertificate(@RequestBody AssociateRequest associateRequest) {

        HashMap<String, String> showAssociate = new HashMap<>();
        try {

            User user = userRepository.findUserByUsername(associateRequest.getUserName());
            List<KeyStoreEntity> allById = keyStoreRepository.findAllById(associateRequest.getKeyStoreIds());
            List<KeyStoreEntity> userCertificates = user.getUserCertificates();
            userCertificates.addAll(allById);
            userRepository.save(user);
            showAssociate.put("Message", "Certificate Associated");
            return ResponseEntity.status(201).body(showAssociate);
        } catch (Exception e) {
            e.printStackTrace();
            showAssociate.put("Message", "Certificate not Associated");
            return ResponseEntity.status(500).body(showAssociate);

        }
    }
    @DeleteMapping("/disassociate")
    public ResponseEntity<Object> deleteAssociateCertificate(@RequestBody AssociateRequest associateRequest)
    {
        try {
            User user = userRepository.findUserByUsername(associateRequest.getUserName());
            List<KeyStoreEntity> allById = keyStoreRepository.findAllById(associateRequest.getKeyStoreIds());
            List<KeyStoreEntity> userCertificates = user.getUserCertificates();
            userCertificates.removeAll(allById);
            userRepository.save(user);
            return ResponseEntity.ok().body(Map.of("Message", "Certificate Disassociated"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("Message","Certificate Disassociation failed"));
        }

    }

}