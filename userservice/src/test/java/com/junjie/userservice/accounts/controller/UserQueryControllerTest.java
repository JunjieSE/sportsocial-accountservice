package com.junjie.userservice.accounts.controller;

import com.junjie.userservice.accounts.model.Users;
import com.junjie.userservice.accounts.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserQueryController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void getUserDetails_ShouldReturnUser_WhenUserExists() throws Exception {
        // Arrange
        String username = "testuser";
        String password = "hashedPassword";

        Users mockUser = new Users();
        mockUser.setUsername(username);
        mockUser.setPassword(password);

        when(userService.findByUsername(username)).thenReturn(mockUser);

        String jsonRequest = """
            {
                "username": "testuser"
            }
        """;

        // Act & Assert
        mockMvc.perform(post("/forAuth/users/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.password").value(password));
    }

    @Test
    void getUserDetails_ShouldReturnNotFound_WhenUserDoesNotExist() throws Exception {
        // Arrange
        String jsonRequest = """
            {
                "username": "nonexistentuser"
            }
        """;

        when(userService.findByUsername("nonexistentuser")).thenReturn(null);

        // Act & Assert
        mockMvc.perform(post("/forAuth/users/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isNotFound());
    }
}
