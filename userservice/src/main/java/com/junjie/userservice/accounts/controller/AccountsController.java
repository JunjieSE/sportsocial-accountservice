package com.junjie.userservice.accounts.controller;

import com.junjie.userservice.accounts.model.dto.ChangeUsernameRequest;
import com.junjie.userservice.accounts.model.dto.UserDeleteRequest;
import com.junjie.userservice.accounts.model.dto.UpdateUsernameGoToTweetMicro;
import com.junjie.userservice.accounts.model.dto.UserRegistrationRequest;
import com.junjie.userservice.accounts.model.Users;

//import com.junjie.SportPost.accounts.service.RabbitMQSender;
import com.junjie.userservice.accounts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountsController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private RabbitMQSender rabbitMQSender;


    @GetMapping("/users")
    public List<Users> getUsers() {
        return userService.allUsers();
    }

    //change username should also trigger the all posts from of the user to change username too.
//    @PutMapping("/{username}/username")
    @PutMapping("/username/{username}")
    public ResponseEntity<String> changeUsername(
            @PathVariable String username,
            @RequestBody ChangeUsernameRequest request) {

        // Update in DB
        Users updatedUser = userService.updateUsername(username, request.getNewUsername());

        // Create DTO to send
        UpdateUsernameGoToTweetMicro dto = new UpdateUsernameGoToTweetMicro(
                username,
                updatedUser.getUsername()
        );


        // Send to RabbitMQ
//        rabbitMQSender.send(dto);

        return ResponseEntity.ok("Username updated and message sent.");
    }


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest request) {
        // Check if the username already exists
        if (userService.isUserAlreadyExist(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }
        else{
            // Save the user to the database using the service
            userService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        }

    }

    @PostMapping("/register/admin")
    public ResponseEntity<String> registerAdmin(@RequestBody UserRegistrationRequest request) {
        // Check if the username already exists
        if (userService.isUserAlreadyExist(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }
        else{
            // Save the user to the database using the service
            userService.registerAdmin(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Admin registered successfully");
        }

    }

    @DeleteMapping("/delete/user")
    public ResponseEntity<String> deleteUser(@RequestBody UserDeleteRequest request){

            Users user = userService.deleteUser(request);

            return ResponseEntity.status(HttpStatus.CREATED).body("user deleted: " + user.getUsername());
    }


    //for developer
//    @DeleteMapping("/delete/{username}")
//    public ResponseEntity<String> deleteInvalid(@PathVariable String username){
//
//        Integer deletedCount = userService.deleteUsersWithInvalidUsername(username);
//        return ResponseEntity.status(HttpStatus.CREATED).body(deletedCount + " users deleted.");
//    }
}
