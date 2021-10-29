package com.example.demo.controllers;

import com.example.demo.exceptions.*;
import com.example.demo.model.Numem;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.services.OrderService;
import com.example.demo.services.ProductService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.standard.expression.OrExpression;
import org.springframework.mail.*;

import java.awt.event.PaintEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

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
        model.addAttribute("numem",new Numem());
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
        model.addAttribute("numem",new Numem());
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        model.addAttribute("produse",ProductService.getAllProducts());
        return "indexLoginGresit";
    }
    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("numem",new Numem());
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        model.addAttribute("prettotal", comanda.pretTotal());
        List<Product> pds=comanda.lista;
        model.addAttribute("produse",pds);
        return "cart";
    }
    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("numem",new Numem());
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
        model.addAttribute("numem",new Numem());
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        List<Product> l =new ArrayList<Product>();
        for(Product p: ProductService.getAllProducts())
            if(p.getQuantity()!=0)
                l.add(p);
        model.addAttribute("produse",l);
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
        model.addAttribute("numem",new Numem());
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
        model.addAttribute("numem",new Numem());
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("user", user);
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("ok2", Boolean.TRUE);
        try
        {
            comanda.setUser(userLogat);
            OrderService.placeOrder(comanda);
            for(Product p: ProductService.getAllProducts())
                if(p.getQuantity()<=0)
                    ProductService.removeProduct(p.getName());
            comanda=new Order();
            model.addAttribute("produse",ProductService.getAllProducts());

        } catch (CartIsEmptyException | ProductDoesNotExist e) {
            return "clientHome";
        }
        return "clientHome";
    }

    @PostMapping("/register")
    public String greetingSubmit(@ModelAttribute User user, Model model) {
        model.addAttribute("numem",new Numem());
        model.addAttribute("user", user);
        List<Product> l =new ArrayList<Product>();
        for(Product p: ProductService.getAllProducts())
            if(p.getQuantity()!=0)
                l.add(p);
        model.addAttribute("produse",l);
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
        userLogat=user.getUsername();
        return "clientHome";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model) {
        model.addAttribute("numem",new Numem());
        model.addAttribute("user",user);
        List<Product> l =new ArrayList<Product>();
        for(Product p: ProductService.getAllProducts())
            if(p.getQuantity()!=0)
                l.add(p);
        model.addAttribute("produse",l);
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
        model.addAttribute("numem",new Numem());
        model.addAttribute("user",user);
        List<Product> l =new ArrayList<Product>();
        for(Product p: ProductService.getAllProducts())
            if(p.getQuantity()!=0)
                l.add(p);
        model.addAttribute("produse",l);
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
            l =new ArrayList<Product>();
            for(Product g: ProductService.getAllProducts())
                if(g.getQuantity()!=0)
                    l.add(g);
            model.addAttribute("produse",l);
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
        model.addAttribute("numem",new Numem());
        model.addAttribute("user", new User());
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("produsul",produsul);
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("ok2", Boolean.TRUE);
        try {
            ProductService.addProduct(produsul.getName(),produsul.getCategory(),produsul.getCode(),produsul.getQuantity(),produsul.getPrice());
            model.addAttribute("produse",ProductService.getAllProducts());
        }
        catch (ProductAlreadyExistsException e)
        {
            model.addAttribute("produse",ProductService.getAllProducts());
            return "indexRegisterGresit";
        }
        model.addAttribute("produse",ProductService.getAllProducts());
        return "adminHome";
    }
    @PostMapping("/stergere")
    public String removeProd(@ModelAttribute Product produsul, Model model) {
        model.addAttribute("numem",new Numem());
        model.addAttribute("user", new User());
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("produsul",produsul);
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("ok2", Boolean.TRUE);
        try {
            ProductService.removeProduct(produsul.getName());
            model.addAttribute("produse",ProductService.getAllProducts());
        }
        catch (ProductDoesNotExist e)
        {
            model.addAttribute("produse",ProductService.getAllProducts());
            return "adminHome";
        }
        model.addAttribute("produse",ProductService.getAllProducts());
        return "adminHome";
    }
    @PostMapping("/admnProd")
    public String adminStergereProdus(@ModelAttribute Product produsul, Model model,@RequestParam(value="numeB",required = false)String k) {
        model.addAttribute("user",new User());
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("numem",new Numem());
        model.addAttribute("ok", Boolean.TRUE);
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("produsul",produsul);
        System.out.println(k+" aaA");
        try {
            ProductService.removeProduct(k);
            model.addAttribute("produse",ProductService.getAllProducts());
        } catch (ProductDoesNotExist doesNotExist) {
            return "adminHome";
        }
        System.out.println(k+" aaA");
        return "adminHome";
    }
    @PostMapping("/admnProd2")
    public String adminStergereProdus2(@ModelAttribute Product produsul, Model model,@RequestParam(value="numeB",required = false)String k) {
        model.addAttribute("user",new User());
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("numem",new Numem());
        model.addAttribute("ok", Boolean.TRUE);
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("produsul",produsul);
        System.out.println(k+" aaA");
        try {
            ProductService.removeProduct(k);
            model.addAttribute("produse",ProductService.getAllProducts());
        } catch (ProductDoesNotExist doesNotExist) {
            return "adminHome";
        }
        System.out.println(k+" aaA");
        return "productsAdmin";
    }
    @PostMapping("/clientPrd")
    public String cleintStergereProdus(@ModelAttribute Product produsul, Model model,@RequestParam(value="numeB",required = false)String k) {
        model.addAttribute("user",new User());
        model.addAttribute("ok", Boolean.TRUE);
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("numem",new Numem());
        model.addAttribute("produsul",produsul);
        System.out.println(k+" aaA");
        ArrayList<Product> l=new ArrayList<Product>();
        for(Product x: comanda.lista)
        {
            if(!k.equals(x.getName()))
                l.add(x);
        }
        comanda.lista=l;
        comanda.contor--;
        model.addAttribute("produse",comanda.lista);
        model.addAttribute("prettotal", comanda.pretTotal());
        return "cart";
    }
    @PostMapping("/decprod")
    public String decprod(@ModelAttribute Product produsul, Model model,@RequestParam(value="numeB",required = false)String k) {
        model.addAttribute("user",new User());
        model.addAttribute("ok", Boolean.TRUE);
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("numem",new Numem());
        model.addAttribute("produsul",produsul);
        System.out.println(k+" aaA");
        ArrayList<Product> l=new ArrayList<Product>();
        for(Product x: comanda.lista)
        {
            if(!k.equals(x.getName()))
                l.add(x);
            else
            {
                x.setQuantity(x.getQuantity()-1);
                l.add(x);
            }
        }
        comanda.lista=l;
        model.addAttribute("produse",comanda.lista);
        model.addAttribute("prettotal", comanda.pretTotal());
        return "cart";
    }
    @PostMapping("/incprod")
    public String incprod(@ModelAttribute Product produsul, Model model,@RequestParam(value="numeB",required = false)String k) {
        model.addAttribute("user",new User());
        model.addAttribute("ok", Boolean.TRUE);
        model.addAttribute("numem",new Numem());
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("produsul",produsul);
        System.out.println(k+" aaA");
        ArrayList<Product> l=new ArrayList<Product>();
        for(Product x: comanda.lista)
        {
            if(!k.equals(x.getName()))
                l.add(x);
            else
            {
                x.setQuantity(x.getQuantity()+1);
                l.add(x);
            }
        }
        comanda.lista=l;
        model.addAttribute("produse",comanda.lista);
        model.addAttribute("prettotal", comanda.pretTotal());
        return "cart";
    }
    @PostMapping("/admnOrd")
    public String adminStergereOrders(@ModelAttribute Product produsul, Model model) {
        model.addAttribute("user",new User());
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("ok", Boolean.TRUE);
        model.addAttribute("numem",new Numem());
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("produsul",produsul);
        OrderService.removeAllOrders();
        model.addAttribute("orders",OrderService.getAllOrders());
        return "adminHome";
    }
    @PostMapping("/removeorder")
    public String removeorderadmin(@ModelAttribute Product produsul, Model model,@RequestParam(value="numeB",required = false)Integer i) {
        model.addAttribute("user",new User());
        model.addAttribute("ok", Boolean.TRUE);
        model.addAttribute("produsul",produsul);
        model.addAttribute("numem",new Numem());
        Order o=OrderService.getAllOrders().get(i);
        orderRepository.remove(o);
        model.addAttribute("orders",OrderService.getAllOrders());
        return "ordersAdmin";
    }

    @PostMapping("/removeorder1")
    public String removeorderadmin1(@ModelAttribute Product produsul, Model model,@RequestParam(value="numeB",required = false)Integer i) {
        model.addAttribute("user",new User());
        model.addAttribute("ok", Boolean.TRUE);
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("numem",new Numem());
        model.addAttribute("produsul",produsul);
        Order o=OrderService.getAllOrders().get(i);
        orderRepository.remove(o);
        model.addAttribute("orders",OrderService.getAllOrders());
        return "adminHome";
    }
    @PostMapping("/adminuser")
    public String seeuserorders(@ModelAttribute Product produsul, Model model,@RequestParam(value="numeB",required = false)String k) {
        model.addAttribute("user",new User());
        model.addAttribute("ok", Boolean.TRUE);
        model.addAttribute("produsul",produsul);
        System.out.println(k+" aaA");
        model.addAttribute("numem",new Numem());
        ArrayList<Order> l=new ArrayList<Order>();
        for(Order x: OrderService.getAllOrders())
        {
            if(k.equals(x.getUser()))
                l.add(x);
        }
        model.addAttribute("orders",l);
        return "ordersAdmin";
    }
    @PostMapping("/seeproducts")
    public String seeproductsadmin(@ModelAttribute Product produsul, Model model,@RequestParam(value="numeB",required = false)Integer i) {
        model.addAttribute("user",new User());
        model.addAttribute("ok", Boolean.TRUE);
        model.addAttribute("produsul",produsul);
        model.addAttribute("numem",new Numem());
        model.addAttribute("orders",OrderService.getAllOrders());
        ArrayList<Product> l= OrderService.getAllOrders().get(i).lista;
        model.addAttribute("produse",l);
        return "productsAdmin2";
    }
    @PostMapping("/seeproducts2")
    public String seeproductsclient(@ModelAttribute Product produsul, Model model,@RequestParam(value="numeB",required = false)String k) {
        model.addAttribute("user",new User());
        model.addAttribute("ok", Boolean.TRUE);
        model.addAttribute("produsul",produsul);
        model.addAttribute("numem",new Numem());
        model.addAttribute("orders",OrderService.getAllOrders());
        Order o=new Order();
        for(Order x:OrderService.getAllOrders())
        {
            if(k.equals(x.chestie))
            {
                o=x;
            }
        }
        model.addAttribute("produse",o.lista);
        System.out.println(o.lista);
        return "productsClient";
    }
    @PostMapping("/seeproducts3")
    public String seeproductsadmin3(@ModelAttribute Product produsul, Model model,@RequestParam(value="numeB",required = false)Integer i) {
        model.addAttribute("user",new User());
        model.addAttribute("ok", Boolean.TRUE);
        model.addAttribute("produsul",produsul);
        model.addAttribute("numem",new Numem());
        model.addAttribute("orders",OrderService.getAllOrders());
        ArrayList<Product> l= OrderService.getAllOrders().get(i).lista;
        model.addAttribute("produse",l);
        return "productsAdmin2";
    }
    @PostMapping("/modify")
    public String modifyadmin(@ModelAttribute Product produsul, Model model,@RequestParam(value="numeB",required = false)String k) throws ProductDoesNotExist {
        model.addAttribute("user",new User());
        model.addAttribute("ok", Boolean.TRUE);
        model.addAttribute("numem",new Numem());
        model.addAttribute("orders",OrderService.getAllOrders());
        Product p=new Product();
        for(Product x:ProductService.getAllProducts())
        {
            if(k.equals(x.getName()))
                p=x;
        }
        ProductService.removeProduct(p.getName());
        model.addAttribute("produsul",p);
        return "modifyProduct";
    }
    @PostMapping("/modificare")
    public String modificareaadmin(@ModelAttribute Product produsul, Model model,@RequestParam(value="numeB",required = false)String k) throws ProductDoesNotExist, ProductAlreadyExistsException {
        model.addAttribute("user",new User());
        model.addAttribute("ok", Boolean.TRUE);
        model.addAttribute("numem",new Numem());
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("produsul",produsul);
        ProductService.addProduct(produsul.getName(),produsul.getCategory(),produsul.getCode(),produsul.getQuantity(),produsul.getPrice());
        model.addAttribute("produse",ProductService.getAllProducts());
        return "adminHome";
    }
    @Autowired
    private JavaMailSender emailSender;
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("noreplymarketapplication@gmail.com");
        mailSender.setPassword("ParolaPtSite1234");

        Properties props = mailSender.getJavaMailProperties();
        ((Properties) props).put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @PostMapping("/sendEmail")
    public String sendemail(@ModelAttribute Numem numem,Model model) {

        model.addAttribute("produsul",new Product());
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("numem",numem);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreplymarketapplication@gmail.com");
        message.setTo("bancila.emanuel953@gmail.com");
        message.setSubject("Email from:"+numem.email+" "+numem.nume);
        message.setText("Email from:"+numem.email+" "+numem.nume+"\nCommented:\n"+numem.comment);
        emailSender.send(message);
        message = new SimpleMailMessage();
        message.setFrom("noreplymarketapplication@gmail.com");
        message.setTo(numem.email);
        message.setSubject("You commented on supermarket application site");
        message.setText("Commented:\n"+numem.comment);
        emailSender.send(message);

        message = new SimpleMailMessage();
        message.setFrom("noreplymarketapplication@gmail.com");
        message.setTo("ciobotaisac@gmail.com");
        message.setSubject("Email from:"+numem.email+" "+numem.nume);
        message.setText("Email from:"+numem.email+" "+numem.nume+"\nCommented:\n"+numem.comment);
        emailSender.send(message);
        return "index";
    }
    @PostMapping("/sendEmail2")
    public String sendemail2(@ModelAttribute Numem numem,Model model) {

        model.addAttribute("produsul",new Product());
        model.addAttribute("orders",OrderService.getAllOrders());
        model.addAttribute("user", new User());
        model.addAttribute("ok", Boolean.FALSE);
        model.addAttribute("ok2", Boolean.FALSE);
        model.addAttribute("produse",ProductService.getAllProducts());
        model.addAttribute("numem",numem);
        numem.nume=userLogat;
        User k=new User();
        for(User u:UserService.getAllUsers())
        {
            if(u.getName().equals(userLogat))
                k=u;
        }
        numem.email=k.getEmail();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreplymarketapplication@gmail.com");
        message.setTo("bancila.emanuel953@gmail.com");
        message.setSubject("Email from:"+numem.email+" "+numem.nume);
        message.setText("Email from:"+numem.email+" "+numem.nume+"\nCommented:\n"+numem.comment);
        emailSender.send(message);
        message = new SimpleMailMessage();
        message.setFrom("noreplymarketapplication@gmail.com");
        message.setTo(numem.email);
        message.setSubject("You commented on supermarket application site");
        message.setText("Commented:\n"+numem.comment);
        emailSender.send(message);
        message = new SimpleMailMessage();
        message.setFrom("noreplymarketapplication@gmail.com");
        message.setTo("ciobotaisac@gmail.com");
        message.setSubject("Email from:"+numem.email+" "+numem.nume);
        message.setText("Email from:"+numem.email+" "+numem.nume+"\nCommented:\n"+numem.comment);
        emailSender.send(message);
        return "clientHome";
    }
}