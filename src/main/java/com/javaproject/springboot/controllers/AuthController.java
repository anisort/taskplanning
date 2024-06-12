package com.javaproject.springboot.controllers;

import com.javaproject.springboot.dto.UserRegistrationDTO;
import com.javaproject.springboot.entities.User;
import com.javaproject.springboot.services.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public User registerUser(@RequestBody @Valid UserRegistrationDTO userDTO) {
        User user = userService.registerNewUserAccount(userDTO);
        return user;
    }

}