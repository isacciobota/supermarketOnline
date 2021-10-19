package com.example.demo.controllers;

import com.example.demo.exceptions.*;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.services.OrderService;
import com.example.demo.services.ProductService;
import com.example.demo.services.UserService;
import org.graalvm.compiler.core.common.type.ArithmeticOpTable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RegisterController {
    @GetMapping("/")
    public String greetingForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        return "index";
    }
    @GetMapping("/ceva")
    public String comanda(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        return "index";
    }

    @PostMapping("/register")
    public String greetingSubmit(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("ok2", Boolean.TRUE);
        try {
            user.setRole("client");
            UserService.addUser(user.getUsername(), user.getPassword(), user.getRole(), user.getName(), user.getAddress(), user.getEmail());
        }
        catch (UsernameAlreadyExistsException e)
        {
            return "index";
        }
        return "clientHome";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model){
        model.addAttribute("user",user);
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("ok", Boolean.TRUE);
        Product p =new Product();
        model.addAttribute("produs",p);
        System.out.println(p.getName());
        System.out.println(p.getName());
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
    @PostMapping("/ceva")
    public String ceva(@ModelAttribute User user, Model model)
    {
        model.addAttribute("user",user);
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("ok", Boolean.TRUE);
        Order order=new Order();
     /*   try {
         //   order.addProduct();
        }
        catch (NotEnoughQuantity e)
        {

        }
        catch (ProductDoesNotExist e)
        {

        }*/
        return "clientHome";
    }
}