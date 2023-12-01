package com.backend.service;

import com.backend.service.configuration.SecurityConfig;
import com.backend.service.controller.CertificateController;
import com.backend.service.controller.UserController;
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
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
@Import(SecurityConfig.class)
@WebMvcTest(UserController.class)

class ServiceApplicationTests {
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
//    void loginUser() throws Exception {
//        User userinput = User.builder().username("RanniRyan123").password("welcome123").build();
//        mockobj.perform(MockMvcRequestBuilders.post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(userinput)))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.content().string("Login Successful"));
//
//    }
//    void createUserSuccess() {
//        when(userrepository.save(any())).thenReturn(User.builder().build());
//        ResponseEntity<String> response = usercontrol.createUser(User.builder().firstName("Ranni").lastName("Riyan").email("Riyan@gmail.com").username("RanniRyan123").role("user").password("welcome123").build());
//        assertNotNull(response);
//        assertEquals(201,response.getStatusCode());
//
//    }

}
