package com.backend.service.handler;

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
    public void saveKeyStore(KeyStore keyStore) {
        try {
            KeyStoreEntity keystoreEntity = new KeyStoreEntity();
            keystoreEntity.setKeystoreData(convertKeyStoreToBytes(keyStore));
            keystoreRepository.save(keystoreEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public KeyStore getKeyStoreById(Long id)
    {
        KeyStoreEntity keyStoreEntity = keystoreRepository.findById(id).get();
        return convertBytesToKeyStore(keyStoreEntity.getKeystoreData());
    }
    public List<KeyStore> getAllKeyStores()
    {
        List<KeyStoreEntity> all = keystoreRepository.findAll();
        List<KeyStore> keyStores = all.stream().map(keyStoreEntity -> convertBytesToKeyStore(keyStoreEntity.getKeystoreData())).collect(Collectors.toList());
        return keyStores;
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
