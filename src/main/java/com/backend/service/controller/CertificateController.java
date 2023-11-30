package com.backend.service.controller;

import com.backend.service.model.Certificate;
import com.backend.service.repository.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class CertificateController {
    @Autowired
    private  CertificateRepository certificateRepository;
    @GetMapping
    public ResponseEntity<List<Certificate>> getCertificate() {
        System.out.println(certificateRepository.findAll());
      return  ResponseEntity.ok().body(certificateRepository.findAll());


    }
}
