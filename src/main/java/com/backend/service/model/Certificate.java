package com.backend.service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column(length = 100000)
    private String pemContent;
    private String domain;
    private String certificateOwner;
    private String issuingAuthority;
    private String subDomain;
    private Date dateOfIssue;
    private Date expirationDate;
    private String publicKey;
    private String fingerPrint;

    // getters and setters

}
