package com.backend.service.controller;

import com.backend.service.handler.KeyStoreService;
import com.backend.service.model.KeyStoreDTO;
import com.backend.service.model.User;
import com.backend.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/certificates")
@CrossOrigin("*")
public class CertificateController {


    @Autowired
    private KeyStoreService keystoreService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/save")
    public ResponseEntity<String> saveKeyStore(@RequestParam String pfxFilePath, @RequestParam String password) throws KeyStoreException, UnsupportedEncodingException {
        String decodedFilePath = URLDecoder.decode(pfxFilePath, "UTF-8");
        String format = decodedFilePath.split("\\.")[1];
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance("PKCS12");
        } catch (KeyStoreException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
        try (FileInputStream fis = new FileInputStream(decodedFilePath)) {
            keyStore.load(fis, password.toCharArray());
        } catch (IOException | CertificateException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
        keystoreService.saveKeyStore(keyStore, format);
        return ResponseEntity.status(201).body("Key Store Created");
    }

    @GetMapping("/{id}")
    public KeyStoreDTO getKeyStoreById(@PathVariable Long id) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {
        KeyStoreDTO keyStoreDTO = keystoreService.getKeyStoreById(id);
        return keyStoreDTO;
    }

    //    @GetMapping
//    public List<KeyStoreDTO> getKeyStores() throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {
//        return keystoreService.getAllKeyStores();
//
//
//    }
    @GetMapping
    public List<KeyStoreDTO> getKeyStores(@RequestParam(required = false) String userName) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {

        List<KeyStoreDTO> allKeyStores = keystoreService.getAllKeyStores();
        if (userName!=null && !userName.isBlank())
        {
            User user = userRepository.findUserByUsername(userName);
            List<Long> keyStoreIds = user.getUserCertificates().stream().map(keyStoreEntity -> keyStoreEntity.getId()).collect(Collectors.toList());
           allKeyStores = allKeyStores.stream().filter(keyStoreDTO -> !keyStoreIds.contains(keyStoreDTO.getId())).collect(Collectors.toList());
        }
        return allKeyStores;
    }


}
