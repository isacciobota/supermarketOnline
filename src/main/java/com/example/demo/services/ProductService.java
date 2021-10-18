package com.example.demo.services;

import com.example.demo.exceptions.ProductAlreadyExistsException;
import com.example.demo.exceptions.ProductDoesNotExist;
import com.example.demo.model.Product;
import com.example.demo.services.FileSystemService;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

public class ProductService {

    public static ObjectRepository<Product> productRepository;

    public static Nitrite database;

    public static void initDatabase() {
        FileSystemService.initDirectory();
        database = Nitrite.builder()
                .filePath(FileSystemService.getPathToFile("Products.db").toFile())
                .openOrCreate("test1", "test1");

        productRepository = database.getRepository(Product.class);
    }

    public static Nitrite getDatabase(){
        return database;
    }

    public static List<Product> getAllProducts(){
        return productRepository.find().toList();
    }

    public static void addProduct(String name, String category, String code, Integer quantity, Integer price) throws ProductAlreadyExistsException {
        checkProductDoesNotAlreadyExist(name);
        productRepository.insert(new Product(name, category, code, quantity, price));
    }

    public static void removeProduct(String name, String code) throws ProductDoesNotExist {
        for (Product product : productRepository.find()) {
            if (Objects.equals(name, product.getName()) && Objects.equals(code, product.getCode())){
                productRepository.remove(product);
                return;
            }
        }
        throw new ProductDoesNotExist(name);
    }

    public static void modifyProduct(String code, String property, String newValue) throws ProductDoesNotExist {
        for (Product product : productRepository.find()) {
            if (Objects.equals(code, product.getCode())){
                if(property.equals("Name"))
                    product.setName(newValue);
                else if(property.equals("Category"))
                    product.setCategory(newValue);
                else if(property.equals("Quantity"))
                    product.setQuantity(Integer.parseInt(newValue));
                else if(property.equals("Price"))
                    product.setPrice(Integer.parseInt(newValue));
                productRepository.update(product);
                return;
            }
        }
        throw new ProductDoesNotExist(code);
    }


    public static void modifyProduct(String name ,Integer newValue) {
        for (Product product : productRepository.find()) {
            if (Objects.equals(name, product.getName())){
                product.setQuantity(newValue);
                productRepository.update(product);
            }
        }
    }

    public static int getPrice(String name ,Integer quantity) {
        int price = 1;
        for (Product product : productRepository.find()) {
            if (Objects.equals(name, product.getName())){
                price = product.getPrice();
            }
        }
        return price * quantity;
    }

    private static void checkProductDoesNotAlreadyExist(String name) throws ProductAlreadyExistsException {
        for (Product product : productRepository.find()) {
            if (Objects.equals(name, product.getName()))
                throw new ProductAlreadyExistsException(name);
        }
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }
}
