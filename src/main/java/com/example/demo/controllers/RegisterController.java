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
import org.graalvm.compiler.lir.LIRInstruction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.standard.expression.OrExpression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.demo.services.OrderService.orderRepository;


@Controller
public class RegisterController {
    public static String userLogat="";
    public static Order comanda=new Order();
    @GetMapping("/")
    public String greetingForm(Model model) {
        model.addAttribute("produsul",new Product());
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        model.addAttribute("produse",ProductService.getAllProducts());
        comanda=new Order();
        return "index";
    }
    @GetMapping("/ceva")
    public String comanda(Model model) {
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        return "index";
    }
    @GetMapping("/loginBad")
    public String loginBad(Model model) {
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        model.addAttribute("produse",ProductService.getAllProducts());
        return "indexLoginGresit";
    }
    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("orders",OrderService.getAllOrders());
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
        model.addAttribute("produse",ProductService.getAllProducts());
        List<Order>o=OrderService.getAllOrders();
        List<Order>k=new ArrayList<Order>();
        for(Order x: o)
        {
            if(userLogat.equals(x.getUser()))
                k.add(x);
        }
        model.addAttribute("orders",k);
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
        model.addAttribute("orders",OrderService.getAllOrders());
        return "clientHome";
    }
    @GetMapping("/adminHome")
    public String adminHome(Model model) {
        model.addAttribute("produsul",new Product());
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        model.addAttribute("orders",OrderService.getAllOrders());
        return "adminHome";
    }
    @GetMapping("/productsAdmin")
    public String productsAdmin(Model model) {
        model.addAttribute("produsul",new Product());
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        return "productsAdmin";
    }
    @GetMapping("/ordersAdmin")
    public String ordersAdmin(Model model) {
        model.addAttribute("produsul",new Product());
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        return "ordersAdmin";
    }
    @GetMapping("/addProduct")
    public String addProduct(Model model) {
        model.addAttribute("produsul",new Product());
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        return "addProduct";
    }
    @GetMapping("/removeProduct")
    public String removeProduct(Model model) {
        model.addAttribute("produsul",new Product());
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        return "removeProduct";
    }

    @GetMapping("/cartButton")
    public String cartButton(@ModelAttribute User user, Model model) {
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("user", user);
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("ok2", Boolean.TRUE);
        try
        {
            comanda.setUser(userLogat);
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
        model.addAttribute("orders",OrderService.getAllOrders());
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
    public String login(@ModelAttribute User user, Model model) {
        model.addAttribute("user",user);
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("produsul",new Product());
        model.addAttribute("ok", Boolean.TRUE);

        /*
                Product p =new Product("numele","categoria","cod",100,20);
        ProductService.addProduct("numele2","categoria","coduu44443ll",100,12);*/
        try{
            userLogat=user.getUsername();
            System.out.println(user.getUsername());
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
        return "indexLoginGresit";
    }
    @PostMapping("/ceva")
    public String ceva(@ModelAttribute User user, Model model,@RequestParam(value="numeB",required = false)String k) {
        model.addAttribute("user",user);
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("ok", Boolean.TRUE);
        model.addAttribute("orders",OrderService.getAllOrders());
        System.out.println(k+" aaA");
        Product p=new Product();
        for(Product x:ProductService.getAllProducts())
        {
            if(k.equals(x.getName()))
            p=x;
        }
        p.setQuantity(1);
        try {
        comanda.addProduct(p);
        }
        catch (NotEnoughQuantity e)
        {
            return "clientHomeBad";
        }
        catch (ProductDoesNotExist e)
        {
            return "clientHome";
        }
        return "clientHome";
    }
    @PostMapping("/adaugare")
    public String addProd(@ModelAttribute Product produsul, Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("produsul",produsul);
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("ok2", Boolean.TRUE);
        try {
            ProductService.addProduct(produsul.getName(),produsul.getCategory(),produsul.getCode(),produsul.getQuantity(),produsul.getPrice());
        }
        catch (ProductAlreadyExistsException e)
        {
            return "indexRegisterGresit";
        }
        return "adminHome";
    }
    @PostMapping("/stergere")
    public String removeProd(@ModelAttribute Product produsul, Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("produsul",produsul);
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("ok2", Boolean.TRUE);
        try {
            ProductService.removeProduct(produsul.getName());
        }
        catch (ProductDoesNotExist e)
        {
            return "adminHome";
        }
        return "adminHome";
    }
    @PostMapping("/admnProd")
    public String adminStergereProdus(@ModelAttribute Product produsul, Model model,@RequestParam(value="numeB",required = false)String k) {
        model.addAttribute("user",new User());
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("ok", Boolean.TRUE);
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("produsul",produsul);
        System.out.println(k+" aaA");
        try {
            ProductService.removeProduct(k);
        } catch (ProductDoesNotExist doesNotExist) {
            return "adminHome";
        }
        System.out.println(k+" aaA");
        return "adminHome";
    }
    @PostMapping("/admnOrd")
    public String adminStergereOrders(@ModelAttribute Product produsul, Model model) {
        model.addAttribute("user",new User());
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("ok", Boolean.TRUE);
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("produsul",produsul);
       OrderService.removeAllOrders();
        return "adminHome";
    }
}