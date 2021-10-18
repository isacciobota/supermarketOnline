package com.example.demo.exceptions;

public class ProductAlreadyExistsException extends Exception{

    private String name;

    public ProductAlreadyExistsException(String name) {
        super(String.format("The product %s already exists!", name));
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
