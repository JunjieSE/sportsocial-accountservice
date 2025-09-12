package com.junjie.userservice.accounts.service;

import com.junjie.userservice.accounts.model.Users;
import com.junjie.userservice.accounts.model.dto.UserRegistrationRequest;
import com.junjie.userservice.accounts.repo.UsersRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
class UserServiceTest {
    @Mock
    private UsersRepo mockRepo;

    @Mock
    private PasswordEncoder mockEncoder;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(mockRepo, mockEncoder);
    }


    @Test
    void testRegister_ShouldEncodePasswordAndSaveUser() {
        // Arrange


        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername("testuser");
        request.setPassword("plaintext");

        String encodedPassword = "mockEncodedPassword";

        when(mockEncoder.encode("plaintext")).thenReturn(encodedPassword);

        Users savedUser = new Users();
        savedUser.setUsername("testuser");
        savedUser.setPassword(encodedPassword);

        when(mockRepo.save(any(Users.class))).thenReturn(savedUser);

        // Act
        Users result = userService.register(request);

        // Assert
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals(encodedPassword, result.getPassword());

        verify(mockEncoder).encode("plaintext");

        ArgumentCaptor<Users> userCaptor = ArgumentCaptor.forClass(Users.class);
        verify(mockRepo).save(userCaptor.capture());
        Users capturedUser = userCaptor.getValue();

        assertEquals("testuser", capturedUser.getUsername());
        assertEquals(encodedPassword, capturedUser.getPassword());
    }



    @Test
    void testIsUserAlreadyExist_ShouldReturnTrue_WhenUserExists() {
        // Arrange
//        UsersRepo mockRepo = mock(UsersRepo.class);
//        PasswordEncoder mockEncoder = mock(PasswordEncoder.class); // not used in this test
//        UserService userService = new UserService(mockRepo, mockEncoder);

        String username = "existingUser";
        Users user = new Users();
        user.setUsername(username);

        when(mockRepo.findByUsername(username)).thenReturn(user);

        // Act
        Boolean result = userService.isUserAlreadyExist(username);

        // Assert
        assertTrue(result);
        verify(mockRepo).findByUsername(username);
    }

    @Test
    void testIsUserAlreadyExist_ShouldReturnFalse_WhenUserDoesNotExist() {
        // Arrange
//        UsersRepo mockRepo = mock(UsersRepo.class);
//        PasswordEncoder mockEncoder = mock(PasswordEncoder.class);
//        UserService userService = new UserService(mockRepo, mockEncoder);

        String username = "nonexistentUser";

        when(mockRepo.findByUsername(username)).thenReturn(null);

        // Act
        Boolean result = userService.isUserAlreadyExist(username);

        // Assert
        assertFalse(result);
        verify(mockRepo).findByUsername(username);
    }

    @Test
    void testUpdateUsername_ShouldUpdate_WhenUserExists() {
        // Arrange
//        UsersRepo mockRepo = mock(UsersRepo.class);
//        PasswordEncoder mockEncoder = mock(PasswordEncoder.class);
//        UserService userService = new UserService(mockRepo, mockEncoder);

        String oldUsername = "oldUser";
        String newUsername = "newUser";

        Users existingUser = new Users();
        existingUser.setUsername(oldUsername);
        existingUser.setPassword("hashedPassword");

        Users updatedUser = new Users();
        updatedUser.setUsername(newUsername);
        updatedUser.setPassword("hashedPassword");

        when(mockRepo.findByUsername(oldUsername)).thenReturn(existingUser);
        when(mockRepo.save(any(Users.class))).thenReturn(updatedUser);

        // Act
        Users result = userService.updateUsername(oldUsername, newUsername);

        // Assert
        assertNotNull(result);
        assertEquals(newUsername, result.getUsername());
        assertEquals("hashedPassword", result.getPassword());

        verify(mockRepo).findByUsername(oldUsername);
        verify(mockRepo).save(existingUser);
    }

    @Test
    void testUpdateUsername_ShouldReturnNull_WhenUserDoesNotExist() {
        // Arrange
//        UsersRepo mockRepo = mock(UsersRepo.class);
//        PasswordEncoder mockEncoder = mock(PasswordEncoder.class);
//        UserService userService = new UserService(mockRepo, mockEncoder);

        String oldUsername = "nonexistentUser";
        String newUsername = "newUser";

        when(mockRepo.findByUsername(oldUsername)).thenReturn(null);

        // Act
        Users result = userService.updateUsername(oldUsername, newUsername);

        // Assert
        assertNull(result);
        verify(mockRepo).findByUsername(oldUsername);
        verify(mockRepo, never()).save(any());
    }
    @Test
    void testFindByUsername_ShouldReturnUser() {
        // Arrange
//        UsersRepo mockRepo = mock(UsersRepo.class);
//        PasswordEncoder mockEncoder = mock(PasswordEncoder.class);
//        UserService userService = new UserService(mockRepo, mockEncoder);

        String username = "johndoe";
        Users mockUser = new Users();
        mockUser.setUsername(username);
        mockUser.setPassword("someEncryptedPassword");

        when(mockRepo.findByUsername(username)).thenReturn(mockUser);

        // Act
        Users result = userService.findByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals("someEncryptedPassword", result.getPassword());

        verify(mockRepo).findByUsername(username);
    }

    @Test
    void testAllUsers_ShouldReturnListOfUsers() {
        // Arrange


        List<Users> mockUsers = List.of(
                new Users(0,"user1", "pass1", "USER"),
                new Users(1,"user2", "pass2", "USER")
        );

        when(mockRepo.findAll()).thenReturn(mockUsers);

        // Act
        List<Users> result = userService.allUsers();

        // Assert
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("user2", result.get(1).getUsername());

        verify(mockRepo).findAll();
    }
}