package com.backend.service.handler;

import com.backend.service.model.KeyStoreDTO;
import com.backend.service.model.KeyStoreEntity;
import com.backend.service.repository.KeyStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeyStoreService {
    @Autowired
    private KeyStoreRepository keystoreRepository;
    public void saveKeyStore(KeyStore keyStore,String format) {
        try {
            KeyStoreEntity keystoreEntity = new KeyStoreEntity();
            keystoreEntity.setKeystoreData(convertKeyStoreToBytes(keyStore));
            keystoreEntity.setFormat(format);
            keystoreRepository.save(keystoreEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public KeyStoreDTO getKeyStoreById(Long id)
    {
        KeyStoreEntity keyStoreEntity = keystoreRepository.findById(id).get();
        KeyStore keyStore = convertBytesToKeyStore(keyStoreEntity.getKeystoreData());
        return KeyStoreDTO.fromKeyStore(keyStore, keyStoreEntity.getFormat());
    }
    public List<KeyStoreDTO> getAllKeyStores()
    {
        List<KeyStoreEntity> all = keystoreRepository.findAll();
        List<KeyStoreDTO> keyStoreDTOS = all.stream().map(keyStoreEntity -> KeyStoreDTO.fromKeyStore(convertBytesToKeyStore(keyStoreEntity.getKeystoreData()), keyStoreEntity.getFormat())).collect(Collectors.toList());
        return keyStoreDTOS;
    }
    public byte[] convertKeyStoreToBytes(KeyStore keyStore) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            keyStore.store(bos, "password".toCharArray());
            return bos.toByteArray();
        } catch (Exception e) {
            throw new IOException("Error converting KeyStore to bytes", e);
        }
    }
    public KeyStore convertBytesToKeyStore(byte[] keystoreData) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(keystoreData)) {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(bis, "password".toCharArray());
            return keyStore;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
