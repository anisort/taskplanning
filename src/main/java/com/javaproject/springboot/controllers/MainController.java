package com.javaproject.springboot.controllers;

import com.javaproject.springboot.dto.UserRegistrationDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserRegistrationDTO user = new UserRegistrationDTO();
        model.addAttribute("user", user);
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/tasks")
    public String getTasksPage() {
        return "tasks";
    }
}