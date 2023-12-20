package com.backend.service;

import com.backend.service.configuration.SecurityConfig;
import com.backend.service.controller.AdminController;
import com.backend.service.controller.UserController;
import com.backend.service.model.AssociateRequest;
import com.backend.service.model.User;
import com.backend.service.repository.KeyStoreRepository;
import com.backend.service.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Import(SecurityConfig.class)
@WebMvcTest(AdminController.class)

public class AdminControllerTest {
    @MockBean
    UserRepository userrepository;
    @MockBean
    KeyStoreRepository keyStoreRepository;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockobj;
    @Test
    public void associateSuccess() throws Exception {
        AssociateRequest associateDummy = AssociateRequest.builder().keyStoreIds(List.of(1L, 2L, 3L)).userName("Ranni").build();
        User dummyUser = User.builder().username("Ranni").password("wel").id(1L).lastName("reen").email("SA@gmail.com").firstName("Ed").role("user").userCertificates(new ArrayList<>()).build();
        when(userrepository.findUserByUsername(any())).thenReturn(dummyUser);
        mockobj.perform(MockMvcRequestBuilders.post("/admin/associate").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(associateDummy)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Message").value("Certificate Associated"));
    }
    @Test
    public void disAssociateSuccess() throws Exception {
        AssociateRequest associateDummy = AssociateRequest.builder().keyStoreIds(List.of(1L, 2L, 3L)).userName("Ranni").build();
        User dummyUser = User.builder().username("Ranni").password("wel").id(1L).lastName("reen").email("SA@gmail.com").firstName("Ed").role("user").userCertificates(new ArrayList<>()).build();
        when(userrepository.findUserByUsername(any())).thenReturn(dummyUser);
        mockobj.perform(MockMvcRequestBuilders.delete("/admin/disassociate").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(associateDummy)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Message").value("Certificate Disassociated"));
    }
}
