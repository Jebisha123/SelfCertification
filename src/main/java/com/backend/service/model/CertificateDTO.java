package com.backend.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Key;
import java.util.Date;
import java.util.List;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateDTO {
    private String alias;
    private String subject;
    private List<?> alternativeNames;
    private String issuer;
    private String serialNumber;
    private Date notBefore;
    private Date notAfter;
    private String publicKeyAlgorithm;

}
