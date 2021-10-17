package com.example.demo.controllers;

import com.example.demo.exceptions.AccountExists;
import com.example.demo.exceptions.UsernameAlreadyExistsException;
import com.example.demo.model.User;
import com.example.demo.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    @GetMapping("/")
    public String greetingForm(Model model) {
        model.addAttribute("user", new User());
        return "index";
    }

    @PostMapping("/register")
    public String greetingSubmit(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        try {
            user.setRole("client");
            UserService.addUser(user.getUsername(), user.getPassword(), user.getRole(), user.getName(), user.getAddress(), user.getEmail());
        }
        catch (UsernameAlreadyExistsException e)
        {
            return "index";
        }
        return "index";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model){
        model.addAttribute("user",user);
        try{
            UserService.checkUsernameAndPassword(user.getUsername(),user.getPassword());
        }
        catch(AccountExists e)
        {
            if(UserService.getUserRole(user.getUsername(),user.getPassword()).equals("client"))
            {
                return "clientHome";
            }
            return "adminHome";
        }
        return "index";
    }
}