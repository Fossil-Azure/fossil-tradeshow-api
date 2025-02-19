package com.fossil.tradeshow.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.fossil.tradeshow.model.Users;
import com.fossil.tradeshow.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;

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
}
