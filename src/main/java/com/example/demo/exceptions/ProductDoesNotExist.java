package com.example.demo.exceptions;

public class ProductDoesNotExist extends Exception {

    private String name;

    public ProductDoesNotExist(String name) {
        super(String.format("The product %s doesn't exists!", name));
        this.name = name;
    }

    public String getName() {
        return name;
    }
}