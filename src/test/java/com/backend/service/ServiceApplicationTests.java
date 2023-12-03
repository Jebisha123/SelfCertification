package com.backend.service;

import com.backend.service.configuration.SecurityConfig;
import com.backend.service.controller.CertificateController;
import com.backend.service.controller.UserController;
import com.backend.service.model.LoginCredentials;
import com.backend.service.repository.CertificateRepository;
import com.backend.service.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.backend.service.model.User;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
@Import(SecurityConfig.class)
@WebMvcTest(UserController.class)

class ServiceApplicationTests {
    @MockBean
    PasswordEncoder encoder;
    @Autowired
    MockMvc mockobj;

    @MockBean
    UserRepository userrepository;
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

}
