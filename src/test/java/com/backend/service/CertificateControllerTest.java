package com.backend.service;

import com.backend.service.configuration.SecurityConfig;
import com.backend.service.controller.CertificateController;
import com.backend.service.handler.KeyStoreService;
import com.backend.service.model.KeyStoreDTO;
import com.backend.service.model.User;
import com.backend.service.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(CertificateController.class)
@Import(SecurityConfig.class)
public class CertificateControllerTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    KeyStoreService keyStoreService;
    @Autowired
    MockMvc mockobj;
    @Test
    public void getAllCertificatesSuccess() throws Exception {
        when(keyStoreService.getAllKeyStores()).thenReturn(List.of(KeyStoreDTO.builder().build(), KeyStoreDTO.builder().build()));

        mockobj.perform(MockMvcRequestBuilders.get("/certificates"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }


}
