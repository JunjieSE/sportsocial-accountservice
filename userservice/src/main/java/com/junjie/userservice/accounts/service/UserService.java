package com.junjie.userservice.accounts.service;

import com.junjie.userservice.accounts.model.Users;
import com.junjie.userservice.accounts.model.dto.UserRegistrationRequest;
import com.junjie.userservice.accounts.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UsersRepo repo;

//    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final PasswordEncoder encoder;


    @Autowired
    public UserService(UsersRepo repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public List<Users> allUsers() {
        return repo.findAll();
    }

    public Users register(UserRegistrationRequest request){

        Users newUser = new Users();
        String encodedPassword = encoder.encode(request.getPassword());

        newUser.setUsername(request.getUsername());
        newUser.setPassword(encodedPassword);
        newUser.setRole("USER");

        return repo.save(newUser);
    }

    public Users registerAdmin(UserRegistrationRequest request){

        Users newUser = new Users();
        String encodedPassword = encoder.encode(request.getPassword());

        newUser.setUsername(request.getUsername());
        newUser.setPassword(encodedPassword);
        newUser.setRole("ADMIN");

        return repo.save(newUser);
    }


    public Boolean isUserAlreadyExist(String username){
        Users user = repo.findByUsername(username);
        if(user == null){
            return false;
        }
        return true;
    }

    public Users updateUsername(String username, String newUsername) {
        Users oldUser = repo.findByUsername(username);
        if(oldUser == null){
            return null;
        }
        oldUser.setUsername(newUsername);
        return repo.save(oldUser);
    }

    public Users findByUsername(String username) {
        Users user = repo.findByUsername(username);
        return  user;
    }
}
