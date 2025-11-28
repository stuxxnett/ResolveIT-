package com.resolveit.service;

import com.resolveit.dto.LoginRequest;
import com.resolveit.dto.UserResponse;
import com.resolveit.model.User;
import com.resolveit.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepo;

    public AuthService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public UserResponse login(LoginRequest request) {

        if (request.getUsername() == null || request.getPassword() == null) {
            throw new RuntimeException("Username or password missing");
        }

        User user = userRepo.findByUsername(request.getUsername()).orElse(null);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        UserResponse res = new UserResponse();
        res.setId(user.getId());
        res.setName(user.getName());
        res.setUsername(user.getUsername());
        res.setRole(user.getRole());

        return res;
    }
}
