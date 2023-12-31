package com.backend.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.*;

@Data
@AllArgsConstructor
@Builder
public class KeyStoreDTO {
    private List<CertificateDTO> certificates;
//    private String pkcs12Format;
    private Long id;
//    private String certName;

    public KeyStoreDTO() {
        this.certificates = new ArrayList<>();
    }
    public static KeyStoreDTO fromKeyStore(KeyStore keyStore, String format,Long id) {
        KeyStoreDTO keyStoreDTO = new KeyStoreDTO();
//        keyStoreDTO.setPkcs12Format(format);
        keyStoreDTO.setId(id);

        try {
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                Certificate certificate = keyStore.getCertificate(alias);

                if (certificate instanceof X509Certificate) {
                    X509Certificate x509Certificate = (X509Certificate) certificate;

                    CertificateDTO certificateDTO = new CertificateDTO();
                    certificateDTO.setAlias(alias);
//                    keyStoreDTO.setCertName(alias);
//                    certificateDTO.setAlternativeNames((List<?>) getAlternativeNames(x509Certificate));
                    certificateDTO.setIssuer(x509Certificate.getIssuerDN().getName());
                    certificateDTO.setSerialNumber(String.valueOf(x509Certificate.getSerialNumber()));
                    certificateDTO.setSignatureAlgorithm(x509Certificate.getSigAlgName());
//                    certificateDTO.setVersion(String.valueOf(x509Certificate.getVersion()));
                    certificateDTO.setValidFrom(x509Certificate.getNotBefore());
                    certificateDTO.setValidTo(x509Certificate.getNotAfter());
                    certificateDTO.setPublicKeyAlgorithm(x509Certificate.getPublicKey().getAlgorithm());
                    keyStoreDTO.getCertificates().add(certificateDTO);

                }
            }
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
        }

        return keyStoreDTO;
    }



    private static Collection<List<?>> getAlternativeNames(X509Certificate certificate) throws CertificateParsingException {
        if(certificate.getIssuerAlternativeNames() != null)
            return certificate.getIssuerAlternativeNames();

        return Collections.emptyList();
    }
}
