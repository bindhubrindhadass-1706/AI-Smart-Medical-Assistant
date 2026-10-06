
package com.brindha.medicalassistant.service;

import com.brindha.medicalassistant.entity.User;
import com.brindha.medicalassistant.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loginUser(String email, String password) {

        System.out.println("========== LOGIN CHECK ==========");
        System.out.println("LOGIN EMAIL: [" + email + "]");
        System.out.println("LOGIN PASSWORD: [" + password + "]");

        User user = userRepository.findByEmail(email);

        if (user == null) {
            System.out.println("USER NOT FOUND");
            System.out.println("=================================");
            return null;
        }

        System.out.println("DATABASE EMAIL: [" + user.getEmail() + "]");
        System.out.println("DATABASE PASSWORD: [" + user.getPassword() + "]");

        if (user.getPassword().equals(password)) {
            System.out.println("LOGIN SUCCESS");
            System.out.println("=================================");
            return user;
        }

        System.out.println("PASSWORD DOES NOT MATCH");
        System.out.println("=================================");

        return null;
    }
}