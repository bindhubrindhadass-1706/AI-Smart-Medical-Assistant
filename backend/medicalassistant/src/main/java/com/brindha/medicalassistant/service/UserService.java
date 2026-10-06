
package com.brindha.medicalassistant.service;

import org.springframework.stereotype.Service;

import com.brindha.medicalassistant.entity.User;
import com.brindha.medicalassistant.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public User loginUser(String email, String password) {

        System.out.println("========== LOGIN CHECK ==========");
        System.out.println("LOGIN EMAIL: [" + email + "]");
        System.out.println("LOGIN PASSWORD: [" + password + "]");

        if (email == null || password == null) {
            System.out.println("EMAIL OR PASSWORD IS NULL");
            return null;
        }

        User user = userRepository.findByEmail(email);

        if (user == null) {
            System.out.println("USER NOT FOUND");
            return null;
        }

        System.out.println("DATABASE EMAIL: [" + user.getEmail() + "]");
        System.out.println("DATABASE PASSWORD: [" + user.getPassword() + "]");

        if (user.getPassword() != null
                && user.getPassword().equals(password)) {

            System.out.println("LOGIN SUCCESS");
            System.out.println("=================================");
            return user;
        }

        System.out.println("PASSWORD NOT MATCHING");
        System.out.println("=================================");

        return null;
    }
}