
package com.brindha.medicalassistant.controller;

import com.brindha.medicalassistant.entity.User;
import com.brindha.medicalassistant.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "http://localhost:5500"
})
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {

        System.out.println("========== LOGIN REQUEST ==========");
        System.out.println("LOGIN EMAIL: [" + user.getEmail() + "]");

        User loggedInUser = userService.loginUser(
                user.getEmail(),
                user.getPassword()
        );

        if (loggedInUser == null) {
            return ResponseEntity
                    .status(401)
                    .body("Invalid email or password");
        }

        System.out.println("LOGIN SUCCESS");
        System.out.println("===================================");

        return ResponseEntity.ok(loggedInUser);
    }
}