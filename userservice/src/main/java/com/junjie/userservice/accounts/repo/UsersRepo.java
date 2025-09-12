package com.junjie.userservice.accounts.repo;

import com.junjie.userservice.accounts.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);
}
