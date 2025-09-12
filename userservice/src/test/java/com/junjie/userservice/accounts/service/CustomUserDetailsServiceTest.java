package com.junjie.userservice.accounts.service;

import com.junjie.userservice.accounts.model.Users;
import com.junjie.userservice.accounts.repo.UsersRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UsersRepo repo;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    @Test
    void loadUserByUsername_ShouldReturnCustomUserDetails_WhenUserExists() {
        // Arrange
        Users mockUser = new Users();
        mockUser.setUsername("johndoe");
        mockUser.setPassword("hashedPassword");

        when(repo.findByUsername("johndoe")).thenReturn(mockUser);

        // Act
        UserDetails result = userDetailsService.loadUserByUsername("johndoe");

        // Assert
        assertNotNull(result);
        assertEquals("johndoe", result.getUsername());
        assertEquals("hashedPassword", result.getPassword());
    }

    @Test
    void loadUserByUsername_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        when(repo.findByUsername("unknown")).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("unknown");
        });
    }
}
