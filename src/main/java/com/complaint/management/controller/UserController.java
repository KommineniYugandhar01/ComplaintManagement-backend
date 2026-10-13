
package com.complaint.management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.complaint.management.entity.User;
import com.complaint.management.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {
    "http://localhost:5173",
    "http://localhost:5174",
    "https://complaint-management-frontend-rose.vercel.app",
    "https://complaint-management-frontend-548drn90b-kommineniyugandhar01.vercel.app"
})
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(
            @RequestBody User user) {

        if (user.getName() == null || user.getName().isBlank()
                || user.getEmail() == null || user.getEmail().isBlank()
                || user.getPassword() == null || user.getPassword().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Name, email, and password are required"
            );
        }

        User registeredUser = userService.registerUser(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registeredUser);
    }

    // Log in an existing user
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(
            @RequestBody User user) {

        if (user.getEmail() == null || user.getEmail().isBlank()
                || user.getPassword() == null || user.getPassword().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Email and password are required"
            );
        }

        User loggedInUser = userService.loginUser(
                user.getEmail(),
                user.getPassword()
        );

        return ResponseEntity.ok(loggedInUser);
    }
}
