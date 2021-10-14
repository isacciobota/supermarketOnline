package com.example.demo.controllers;

import com.example.demo.exceptions.UsernameAlreadyExistsException;
import com.example.demo.model.User;
import com.example.demo.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    @GetMapping("/register")
    public String greetingForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String greetingSubmit(@ModelAttribute User user, Model model) throws UsernameAlreadyExistsException {
        model.addAttribute("user", user);
        try {
            UserService.addUser(user.getUsername(), user.getPassword(), user.getRole(), user.getName(), user.getAddress(), user.getEmail());
        }
        catch (UsernameAlreadyExistsException e)
        {
            return "register2";
        }
        return "rez";
    }
}