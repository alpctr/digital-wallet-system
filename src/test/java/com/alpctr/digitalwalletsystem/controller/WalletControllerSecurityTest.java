package com.alpctr.digitalwalletsystem.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    // 1. EMPLOYEE should access any wallet
    @Test
    @WithMockUser(username = "00000000000", roles = {"EMPLOYEE"})
    void employeeCanAccessAnyWallet() throws Exception {
        mockMvc.perform(get("/api/wallets")
                .param("customerId", "5"))
                .andExpect(status().isOk());
    }
    

    // 2. CUSTOMER can only access their own wallet
    @Test
    @WithMockUser(username = "56789012345", roles = {"CUSTOMER"})
    void customerCanAccessOwnWallet() throws Exception {
        mockMvc.perform(get("/api/wallets")
                .param("customerId", "5"))  // Must belong to TCKN=56789012345 in DB
                .andExpect(status().isOk());
    }

    // 3. CUSTOMER cannot access another customer's wallet
    @Test
    @WithMockUser(username = "99999999999", roles = {"CUSTOMER"})
    void customerCannotAccessOtherWallet() throws Exception {
        mockMvc.perform(get("/api/wallets")
                .param("customerId", "5"))  // Belongs to a different TCKN
                .andExpect(status().isForbidden());
    }
}


