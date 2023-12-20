package com.backend.service;

import com.backend.service.configuration.SecurityConfig;
import com.backend.service.controller.UserController;
import com.backend.service.handler.KeyStoreService;
import com.backend.service.model.KeyStoreDTO;
import com.backend.service.model.KeyStoreEntity;
import com.backend.service.model.LoginCredentials;
import com.backend.service.repository.KeyStoreRepository;
import com.backend.service.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.backend.service.model.User;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.*;
@Import(SecurityConfig.class)
@WebMvcTest(UserController.class)

class ServiceApplicationTests {
    @MockBean
    PasswordEncoder encoder;
    @Autowired
    MockMvc mockobj;
    @Mock
    KeyStore keyStore;
    @MockBean
    KeyStoreRepository keystoreRepository;
    @MockBean
    UserRepository userrepository;
    @MockBean
    KeyStoreService keyStoreService;
    @Autowired
    ObjectMapper objectMapper;
    @Test
    void createUserSuccess() throws Exception {
        User userinput = User.builder().firstName("Ranni").lastName("Riyan").email("Riyan@gmail.com").username("RanniRyan123").role("user").password("welcome123").build();
        mockobj.perform(MockMvcRequestBuilders.post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(userinput)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("User Registered"));

    }
    @Test
    void loginUserSuccess() throws Exception {

        LoginCredentials login = new LoginCredentials("Ranni","welcome123");
        when(encoder.matches(any(),any())).thenReturn(true);
        when(userrepository.findUserByUsername(any())).thenReturn(User.builder().username("Ranni").password("welcome123").build());
        mockobj.perform(MockMvcRequestBuilders.post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(login)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Login Successful"));
    }

    @Test
    void loginUserFailed() throws Exception {
        LoginCredentials login = new LoginCredentials("Ranni","welcome1234");
        when(encoder.matches(any(),any())).thenReturn(false);
        when(userrepository.findUserByUsername(any())).thenReturn(User.builder().username("Ranni").password("welcome123").build());
        mockobj.perform(MockMvcRequestBuilders.post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(login)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("Login Failed"));
    }
    @Test
    void getmycertificatesSuccess() throws Exception {
        List<KeyStoreDTO> keyStoreEntities = new ArrayList<>();
        keyStoreEntities.add(KeyStoreDTO.builder().build());
        keyStoreEntities.add(KeyStoreDTO.builder().build());
        when(keyStoreService.convertKeystoreEntitytoDTO(anyList())).thenReturn(keyStoreEntities);
        User dummyUser = User.builder().username("Ranni").password("wel").id(1L).lastName("reen").email("SA@gmail.com").firstName("Ed").role("user").userCertificates(new ArrayList<>()).build();
        when(userrepository.findUserByUsername(any())).thenReturn(dummyUser);
        mockobj.perform(MockMvcRequestBuilders.get("/auth/getmycertificates").param("userName","Ranni"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));

    }


}
