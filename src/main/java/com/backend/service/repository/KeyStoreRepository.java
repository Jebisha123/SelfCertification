package com.backend.service.repository;

import com.backend.service.model.KeyStoreDTO;
import com.backend.service.model.KeyStoreEntity;
import com.backend.service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyStoreRepository extends JpaRepository<KeyStoreEntity,Long> {

}
