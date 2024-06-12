package com.javaproject.springboot.controllers;

import com.javaproject.springboot.dto.UserDTO;
import com.javaproject.springboot.entities.User;
import com.javaproject.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public UserDTO getCurrentUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByName(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserDTO(currentUser.getId(), currentUser.getName(), currentUser.getRole());
    }
}
