package com.junjie.userservice.accounts.controller;

import com.junjie.userservice.accounts.model.Users;
import com.junjie.userservice.accounts.service.RabbitMQSender;
import com.junjie.userservice.accounts.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(AccountsController.class)
@AutoConfigureMockMvc
class AccountsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private RabbitMQSender rabbitMQSender;

    @Test
    @WithMockUser(username = "admin", roles = {"USER"}) // Simulate authentication
    void getUsers_ShouldReturnListOfUsers() throws Exception {
        // Arrange
        Users user1 = new Users();
        user1.setUsername("user1");
        user1.setPassword("pw1");

        Users user2 = new Users();
        user2.setUsername("user2");
        user2.setPassword("pw2");

        List<Users> users = List.of(user1, user2);
        when(userService.allUsers()).thenReturn(users);

        // Act & Assert
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("user1"))
                .andExpect(jsonPath("$[1].username").value("user2"));
    }

    @Test
    void getUsers_ShouldReturn401_WhenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isUnauthorized()); // 401
    }


}