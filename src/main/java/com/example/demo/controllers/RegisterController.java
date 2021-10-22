package com.example.demo.controllers;

import com.example.demo.exceptions.*;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.model.ViewOrdersTableModel;
import com.example.demo.services.OrderService;
import com.example.demo.services.ProductService;
import com.example.demo.services.UserService;
import org.graalvm.compiler.core.common.type.ArithmeticOpTable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.standard.expression.OrExpression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Controller
public class RegisterController {
    public static String userLogat;
    public static Order comanda=new Order();
    @GetMapping("/")
    public String greetingForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        model.addAttribute("produse",ProductService.getAllProducts());
        comanda=new Order();
        return "index";
    }
    @GetMapping("/ceva")
    public String comanda(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        return "index";
    }
    @GetMapping("/loginBad")
    public String loginBad(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        model.addAttribute("produse",ProductService.getAllProducts());
        return "indexLoginGresit";
    }
    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        model.addAttribute("prettotal", comanda.pretTotal());
        int i=1;
        List<Order> o = new ArrayList<Order>();
        List<Product> pds=comanda.lista;
        model.addAttribute("produse",pds);
        return "cart";
    }
    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        return "orders";
    }
    @GetMapping("/clientHome")
    public String clientHome(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        model.addAttribute("produse",ProductService.getAllProducts());
        return "clientHome";
    }
    @GetMapping("/adminHome")
    public String adminHome(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        return "adminHome";
    }
    @GetMapping("/productsAdmin")
    public String productsAdmin(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        return "productsAdmin";
    }
    @GetMapping("/ordersAdmin")
    public String ordersAdmin(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        return "ordersAdmin";
    }
    @GetMapping("/addProduct")
    public String addProduct(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        return "addProduct";
    }
    @GetMapping("/removeProduct")
    public String removeProduct(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        return "removeProduct";
    }

    @GetMapping("/cartButton")
    public String cartButton(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("ok2", Boolean.TRUE);
        try
        {
            OrderService.placeOrder(comanda);
            comanda=new Order();

        } catch (CartIsEmptyException e) {
            e.printStackTrace();
        }
        return "clientHome";
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
            return "indexRegisterGresit";
        }
        return "clientHome";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model) throws ProductAlreadyExistsException {
        model.addAttribute("user",user);
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("ok", Boolean.TRUE);/*
        Product p =new Product("numele","categoria","cod",100,20);
        ProductService.addProduct("numele","categoria","coduull",100,12);*/
        try{
            UserService.checkUsernameAndPassword(user.getUsername(),user.getPassword());
            userLogat=user.getUsername();
        }
        catch(AccountExists e)
        {
            if(UserService.getUserRole(user.getUsername(),user.getPassword()).equals("client"))
            {
                return "clientHome";
            }
            return "adminHome";
        }
        return "indexLoginGresit";
    }
    @PostMapping("/ceva")
    public String ceva(@ModelAttribute User user, Model model,@RequestParam(value="id",required=false) String id) throws ProductDoesNotExist, NotEnoughQuantity, CartIsEmptyException {
        model.addAttribute("user",user);
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("ok", Boolean.TRUE);
        System.out.println(id);
        Product p=ProductService.getAllProducts().get(2);
        comanda.addProduct(p);
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