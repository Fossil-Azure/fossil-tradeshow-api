package com.fossil.tradeshow.controller;

import com.fossil.tradeshow.model.Users;
import com.fossil.tradeshow.service.AuthService;
import com.fossil.tradeshow.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/addUser")
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        Users createdUser = usersService.addUser(user);
        System.out.println("Controller" + user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping(value = "/getAllUsers")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> allUsers = usersService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping(value = "/getUser/{emailId}")
    public ResponseEntity<Users> getUserDetails(@PathVariable String emailId) {
        return usersService.getUserData(emailId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/update-password")
    public ResponseEntity<Map<String, String>> updatePassword(
            @RequestParam String email,
            @RequestParam String newPassword
    ) {
        String response = authService.updatePassword(email, newPassword);
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", response);

        if (response.equals("User not found") || response.equals("Incorrect old password")) {
            return ResponseEntity.status(400).body(responseBody);
        }

        return ResponseEntity.ok(responseBody);
    }
}
