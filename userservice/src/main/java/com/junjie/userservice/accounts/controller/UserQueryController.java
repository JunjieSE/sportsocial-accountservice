package com.junjie.userservice.accounts.controller;

import com.junjie.userservice.accounts.model.Users;
import com.junjie.userservice.accounts.model.dto.UserDetailsResponse;
import com.junjie.userservice.accounts.model.dto.UserLoginRequest;
import com.junjie.userservice.accounts.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/forAuth/users")
public class UserQueryController {

    @Autowired
    private UserService userService;

    @PostMapping("/find")
    public ResponseEntity<UserDetailsResponse> getUserDetails(@RequestBody UserLoginRequest request) {
        Users user = userService.findByUsername(request.getUsername());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserDetailsResponse response = new UserDetailsResponse(
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );

        return ResponseEntity.ok(response);
    }
}

