package com.supersection.journalApp.controller;

import com.supersection.journalApp.enitity.UserEntity;
import com.supersection.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @GetMapping("health-check")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("create-user")
    public ResponseEntity<?> createUser(@RequestBody UserEntity user) {
        try {
            if (user.getUsername() == null || user.getUsername().isEmpty()) {
                return new ResponseEntity<>("Username is required.", HttpStatus.BAD_REQUEST);
            }
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                return new ResponseEntity<>("Password is required", HttpStatus.UNAUTHORIZED);
            }
            userService.saveNewUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
